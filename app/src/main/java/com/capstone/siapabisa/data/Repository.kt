package com.capstone.siapabisa.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.siapabisa.data.remote.model.Login
import com.capstone.siapabisa.data.remote.ApiConfig
import com.capstone.siapabisa.data.remote.ApiService
import com.capstone.siapabisa.data.remote.ResponseBiodata
import com.capstone.siapabisa.data.remote.ResponseEditBiodata
import com.capstone.siapabisa.data.remote.ResponseProfilUsaha
import com.capstone.siapabisa.data.remote.ResponseRegister
import com.capstone.siapabisa.data.remote.ResponseSubmitBiodata
import com.capstone.siapabisa.data.remote.model.BiodataEdit
import com.capstone.siapabisa.data.remote.model.BiodataEditData
import com.capstone.siapabisa.data.remote.model.BiodataSubmit
import com.capstone.siapabisa.data.remote.model.BiodataSubmitData
import com.capstone.siapabisa.data.remote.model.Birthday
import com.capstone.siapabisa.data.remote.model.DetailLoker
import com.capstone.siapabisa.data.remote.model.Job
import com.capstone.siapabisa.dummy.Jobs
import com.capstone.siapabisa.dummy.jobList

class Repository(private val apiService: ApiService, private val context: Context) {

    private val _responseLogin = MutableLiveData<Result<Login>>()
    val responseLogin: LiveData<Result<Login>> = _responseLogin

    private val _responseRegister = MutableLiveData<Result<ResponseRegister>>()
    val responseRegister: LiveData<Result<ResponseRegister>> = _responseRegister

    private val _responseBiodata = MutableLiveData<Result<ResponseBiodata>>()
    val responseBiodata: LiveData<Result<ResponseBiodata>> = _responseBiodata

    private val _responseCheckBiodata = MutableLiveData<Boolean>()
    val responseCheckBiodata: LiveData<Boolean> = _responseCheckBiodata

    private val _responseSubmitBiodata = MutableLiveData<Result<ResponseSubmitBiodata>>()
    val responseSubmitBiodata: LiveData<Result<ResponseSubmitBiodata>> = _responseSubmitBiodata

    private val _responseEditBiodata = MutableLiveData<Result<ResponseEditBiodata>>()
    val responseEditBiodata: LiveData<Result<ResponseEditBiodata>> = _responseEditBiodata

    private val _responseProfilUsaha = MutableLiveData<Result<ResponseProfilUsaha>>()
    val responseProfilUsaha: LiveData<Result<ResponseProfilUsaha>> = _responseProfilUsaha



    suspend fun login(username : String, password : String){
        try {
            val response = ApiConfig.getApiService().login(username, password)
            if (response.isSuccessful) {
                val result = response.body()
                result?.data?.let{
                        _responseLogin.value = Result.Success(it)
                }
            } else {
                _responseLogin.value = Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            _responseLogin.value = Result.Error(e.message.toString())
        }
    }

    suspend fun register(username : String, email : String, password : String, role : String){
        try {
            val response = ApiConfig.getApiService().register(username, email, password, role)
            if (response.isSuccessful) {
                val result = response.body()
                result?.let{
                    _responseRegister.value = Result.Success(it)
                }
            } else {
                _responseRegister.value = Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            _responseRegister.value = Result.Error(e.message.toString())
        }
    }

    suspend fun getBiodata(userid:String){
        try {
            val response = ApiConfig.getApiService().getBiodata(userid)
            if (response.isSuccessful) {
                val result = response.body()
                result?.let{
                    _responseBiodata.value = Result.Success(it)
                }
            } else {
                _responseBiodata.value = Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            _responseBiodata.value = Result.Error(e.message.toString())
        }
    }

    suspend fun submitBiodata(userId : String,nama : String, birthday : Birthday, alamat : String, deskripsiDiri : String, pendidikan : String, pengalaman : String, keterampilan : String, peminatan : String){
        try {

            val biodataInner = BiodataSubmitData(nama,birthday)
            val biodataBody = BiodataSubmit(userId,biodataInner,alamat,deskripsiDiri,pendidikan,pengalaman,keterampilan,peminatan)

            val response = ApiConfig.getApiService().submitBiodata(biodataBody)
            if (response.isSuccessful) {
                val result = response.body()
                result?.let{
                    _responseSubmitBiodata.value = Result.Success(it)
                }
            } else {
                _responseSubmitBiodata.value = Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            _responseSubmitBiodata.value = Result.Error(e.message.toString())
        }
    }

    suspend fun editBiodata(id:String,nama: String, birthday : Birthday, alamat : String, deskripsiDiri : String, pendidikan : String, pengalaman : String, keterampilan : String, peminatan : String){
        try {

            val biodataInner = BiodataEditData(nama,birthday)
            val biodataBody = BiodataEdit(id,biodataInner,alamat,deskripsiDiri,pendidikan,pengalaman,keterampilan,peminatan)

            val response = ApiConfig.getApiService().editBiodata(biodataBody)
            if (response.isSuccessful) {
                val result = response.body()
                result?.let{
                    _responseEditBiodata.value = Result.Success(it)
                }
            } else {
                _responseEditBiodata.value = Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            _responseEditBiodata.value = Result.Error(e.message.toString())
        }
    }

   fun getDummyJobs(): List<Jobs> {
        return jobList
    }

    fun searchDummyJobs(keyword : String) : List<Jobs> {
        return jobList.filter {
            it.namaPerusahaan.lowercase().contains(keyword.lowercase())
        }
    }

    suspend fun getAllJobs():LiveData<Result<List<Job>>> {
        val result = MutableLiveData<Result<List<Job>>>()
        result.value = Result.Loading

        try {
            val response = apiService.getAllJobs()
            if (response.isSuccessful) {
                val Jobs = response.body()!!.data
                result.value = Result.Success(Jobs)
            }

        } catch (e: Exception) {
            result.value = Result.Error(e.message.toString())
        }
        return result
    }

    suspend fun getJob(id:String):LiveData<Result<DetailLoker>> {
        val result = MutableLiveData<Result<DetailLoker>>()
        result.value = Result.Loading

        try{
            val response = apiService.getJob(id)
            if (response.isSuccessful) {
                val job = response.body()!!.data
                result.value = Result.Success(job)
            }
        } catch (e: Exception) {
            result.value = Result.Error(e.message.toString())
        }
        return result

    }

    suspend fun getProfilUsaha(userid:String){
        try {
            val response = ApiConfig.getApiService().getProfilUsaha(userid)
            if (response.isSuccessful) {
                val result = response.body()
                result?.let{
                    _responseProfilUsaha.value = Result.Success(it)
                }
            } else {
                _responseProfilUsaha.value = Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            _responseProfilUsaha.value = Result.Error(e.message.toString())
        }
    }






}