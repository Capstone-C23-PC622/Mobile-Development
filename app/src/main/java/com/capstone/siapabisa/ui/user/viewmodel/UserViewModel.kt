package com.capstone.siapabisa.ui.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.data.remote.ResponseBiodata
import com.capstone.siapabisa.data.Result
import kotlinx.coroutines.launch

class UserViewModel(private val repository: Repository): ViewModel() {

    private val _responseBiodata = repository.responseBiodata
    val responseBiodata: LiveData<Result<ResponseBiodata>> = _responseBiodata

    fun getBiodata(userid:String){
        viewModelScope.launch{
            repository.getBiodata(userid)
        }
    }


}