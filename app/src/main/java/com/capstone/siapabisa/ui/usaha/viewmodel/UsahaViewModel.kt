package com.capstone.siapabisa.ui.usaha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.remote.ResponseProfilUsaha
import kotlinx.coroutines.launch

class UsahaViewModel(private val repository: Repository): ViewModel() {

    private val _responseProfilUsaha = repository.responseProfilUsaha
    val responseProfilUsaha: LiveData<Result<ResponseProfilUsaha>> = _responseProfilUsaha

    fun getProfilUsaha(userid:String){
        viewModelScope.launch{
            repository.getProfilUsaha(userid)
        }
    }

}