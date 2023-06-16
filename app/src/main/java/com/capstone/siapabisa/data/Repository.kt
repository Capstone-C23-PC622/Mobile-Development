package com.capstone.siapabisa.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.siapabisa.data.remote.model.Login
import com.capstone.siapabisa.data.remote.ApiConfig
import com.capstone.siapabisa.data.remote.ApiService
import com.capstone.siapabisa.data.remote.ModelApiConfig
import com.capstone.siapabisa.data.remote.ResponseBiodata
import com.capstone.siapabisa.data.remote.ResponseDelete
import com.capstone.siapabisa.data.remote.ResponseEditBiodata
import com.capstone.siapabisa.data.remote.ResponseEditProfil
import com.capstone.siapabisa.data.remote.ResponsePostLoker
import com.capstone.siapabisa.data.remote.ResponsePredict
import com.capstone.siapabisa.data.remote.ResponseProfilUsaha
import com.capstone.siapabisa.data.remote.ResponseRegister
import com.capstone.siapabisa.data.remote.ResponseSubmitBiodata
import com.capstone.siapabisa.data.remote.ResponseSubmitProfil
import com.capstone.siapabisa.data.remote.model.BiodataEdit
import com.capstone.siapabisa.data.remote.model.BiodataEditData
import com.capstone.siapabisa.data.remote.model.BiodataSubmit
import com.capstone.siapabisa.data.remote.model.BiodataSubmitData
import com.capstone.siapabisa.data.remote.model.BiodataSubmitParent
import com.capstone.siapabisa.data.remote.model.Birthday
import com.capstone.siapabisa.data.remote.model.DetailLoker
import com.capstone.siapabisa.data.remote.model.Job
import com.capstone.siapabisa.data.remote.model.PredictRequest
import com.capstone.siapabisa.dummy.Jobs
import com.capstone.siapabisa.dummy.jobList
import com.capstone.siapabisa.util.BiodataEditSerializer
import com.capstone.siapabisa.util.BiodataSubmitSerializer
import com.capstone.siapabisa.util.BirthdaySerializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response

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

    private val _responseSubmitProfil = MutableLiveData<Result<ResponseSubmitProfil>>()
    val responseSubmitProfil: LiveData<Result<ResponseSubmitProfil>> = _responseSubmitProfil

    private val _responseEditProfil = MutableLiveData<Result<ResponseEditProfil>>()
    val responseEditProfil: LiveData<Result<ResponseEditProfil>> = _responseEditProfil

    private val _responseUpload = MutableLiveData<Result<ResponsePostLoker>>()
    val responseUpload : LiveData<Result<ResponsePostLoker>> = _responseUpload

    private val _responsePredict = MutableLiveData<Result<ResponsePredict>>()
    val responsePredict : LiveData<Result<ResponsePredict>> = _responsePredict

    private val _responseDelete = MutableLiveData<Result<ResponseDelete>>()
    val responseDelete : LiveData<Result<ResponseDelete>> = _responseDelete


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

        val biodataBody = BiodataSubmit(
            userId = userId,
            data = BiodataSubmitData(
                nama = nama,
                birthday = birthday,
                alamat = alamat,
                deskripsiDiri = deskripsiDiri,
                pendidikan = pendidikan,
                pengalaman = pengalaman,
                keterampilan = keterampilan,
                peminatan = peminatan
            )
        )


        try {

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

        val biodataBody = BiodataEdit(
            data = BiodataEditData(
                nama = nama,
                birthday = birthday
            ),
            alamat = alamat,
            deskripsiDiri = deskripsiDiri,
            pendidikan = pendidikan,
            pengalaman = pengalaman,
            keterampilan = keterampilan,
            peminatan = peminatan
        )


        try {
            val response = ApiConfig.getApiService().editBiodata(id,biodataBody)
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

    suspend fun submitProfilUsaha(userId:String,namaUsaha:String,alamat:String,deskripsi:String,bidangUsaha:String){
        try {
            val response = ApiConfig.getApiService().submitProfilUsaha(userId,namaUsaha,alamat,deskripsi,bidangUsaha)
            if (response.isSuccessful) {
                val result = response.body()
                result?.let{
                    _responseSubmitProfil.value = Result.Success(it)
                }
            } else {
                _responseSubmitProfil.value = Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            _responseSubmitProfil.value = Result.Error(e.message.toString())
        }
    }

    suspend fun editProfilUsaha(id:String,userId:String,namaUsaha:String,alamat:String,deskripsi:String,bidangUsaha:String){
        try {
            val response = ApiConfig.getApiService().updateProfilUsaha(id,userId,namaUsaha,alamat,deskripsi,bidangUsaha)
            if (response.isSuccessful) {
                val result = response.body()
                result?.let{
                    _responseEditProfil.value = Result.Success(it)
                }
            } else {
                _responseEditProfil.value = Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            _responseEditProfil.value = Result.Error(e.message.toString())
        }
    }

    suspend fun postLoker(userId: RequestBody,namaPerusahaan: RequestBody,lowongan: RequestBody, jenisLowongan: RequestBody, pendidikan: RequestBody, pengalaman: RequestBody, lokasi: RequestBody, deskripsi: RequestBody, image: MultipartBody.Part){
        _responseUpload.value = Result.Loading

        try{
            val response = ApiConfig.getApiService().postLoker(userId,namaPerusahaan,lowongan,jenisLowongan,pendidikan,pengalaman,lokasi,deskripsi,image)
            if(response.isSuccessful){
                val result = response.body()
                result?.let{
                    _responseUpload.value = Result.Success(it)
                }
            }
            else{
                _responseUpload.value = Result.Error("Error: ${response.code()}")
            }
        }
        catch(e:Exception){
            _responseUpload.value = Result.Error(e.message.toString())
        }

    }

    suspend fun deleteLoker(id:String){
        _responseDelete.value = Result.Loading

        try{
            val response = ApiConfig.getApiService().deleteLoker(id)
            if(response.isSuccessful){
                val result = response.body()
                result?.let{
                    _responseDelete.value = Result.Success(it)
                }
            }
            else{
                _responseDelete.value = Result.Error("Error: ${response.code()}")
            }
        }
        catch(e:Exception){
            _responseDelete.value = Result.Error(e.message.toString())
        }
    }



    suspend fun postPrediction(keterampilan:String,peminatan:String){
     _responsePredict.value = Result.Loading

        val predictRequest = PredictRequest(keterampilan,peminatan)

     try{
            val response = ModelApiConfig.getApiService().getPrediction(predictRequest)
            if(response.isSuccessful){
                val result = response.body()
                result?.let{
                    _responsePredict.value = Result.Success(it)
                }
            }
            else{
                _responsePredict.value = Result.Error("Error: ${response.code()}")
            }
        }
        catch(e:Exception){
            _responsePredict.value = Result.Error(e.message.toString())
     }
    }






}