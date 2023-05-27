package com.capstone.siapabisa.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.siapabisa.data.local.Login
import com.capstone.siapabisa.data.remote.ApiConfig
import com.capstone.siapabisa.data.remote.ApiService
import com.capstone.siapabisa.data.remote.ResponseRegister

class Repository(private val apiService: ApiService, private val context: Context) {

    private val _responseLogin = MutableLiveData<Result<Login>>()
    val responseLogin: LiveData<Result<Login>> = _responseLogin

    private val _responseRegister = MutableLiveData<Result<ResponseRegister>>()
    val responseRegister: LiveData<Result<ResponseRegister>> = _responseRegister

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

}