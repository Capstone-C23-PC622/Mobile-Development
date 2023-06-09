package com.capstone.siapabisa.ui.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.remote.ResponseRegister
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository): ViewModel() {

    private val _responseRegister = repository.responseRegister
    val responseRegister: LiveData<Result<ResponseRegister>> = _responseRegister

    fun register(username : String, email : String, password : String, role : String){
        viewModelScope.launch { repository.register(username, email, password, role) }

    }

}