package com.capstone.siapabisa.ui.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.data.remote.model.Login
import com.capstone.siapabisa.data.Result
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository): ViewModel() {

    private val _responseLogin = repository.responseLogin
    val responseLogin: LiveData<Result<Login>> = _responseLogin

    private val _responseCheckBiodata = repository.responseCheckBiodata
    val responseCheckBiodata: LiveData<Boolean> = _responseCheckBiodata
    fun login(username : String, password : String){
        viewModelScope.launch{
            repository.login(username, password)
        }

    }

    fun checkBiodata(userid:String){
        viewModelScope.launch{
            repository.checkBiodata(userid)
        }
    }

}