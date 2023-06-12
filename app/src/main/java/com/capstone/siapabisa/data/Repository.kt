package com.capstone.siapabisa.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.siapabisa.data.remote.model.Login
import com.capstone.siapabisa.data.remote.ApiConfig
import com.capstone.siapabisa.data.remote.ApiService
import com.capstone.siapabisa.data.remote.ResponseBiodata
import com.capstone.siapabisa.data.remote.ResponseRegister
import com.capstone.siapabisa.data.remote.ResponseSubmitBiodata
import com.capstone.siapabisa.data.remote.model.Birthday
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

    suspend fun checkBiodata(userid:String){
        try {
            val response = ApiConfig.getApiService().getBiodata(userid)
            if (response.isSuccessful) {
                val result = response.body()
                result?.error.let{
                    _responseCheckBiodata.value = it
                }
            } else {
                _responseCheckBiodata.value = true
            }
        } catch (e: Exception) {
            _responseBiodata.value = Result.Error(e.message.toString())
        }
        catch(e: Exception){
            _responseBiodata.value = Result.Error(e.message.toString())
        }
    }

    suspend fun submitBiodata(nama : String, birthday : Birthday, alamat : String, deskripsiDiri : String, pendidikan : String, pengalaman : String, keterampilan : String, peminatan : String){
        try {
            val response = ApiConfig.getApiService().submitBiodata(nama, birthday, alamat, deskripsiDiri, pendidikan, pengalaman, keterampilan, peminatan)
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


}