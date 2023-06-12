package com.capstone.siapabisa.ui.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.data.remote.model.Birthday
import kotlinx.coroutines.launch

class BiodataViewModel(private val repository: Repository): ViewModel() {

    private val _responseSubmitBiodata = repository.responseSubmitBiodata
    val responseSubmitBiodata = _responseSubmitBiodata

    private val _responseCheckBiodata = repository.responseCheckBiodata
    val responseCheckBiodata = _responseCheckBiodata

    fun submitBiodata(nama : String, birthday : Birthday, alamat : String, deskripsiDiri : String, pendidikan : String, pengalaman : String, keterampilan : String, peminatan : String){
        viewModelScope.launch {
            repository.submitBiodata(nama, birthday, alamat, deskripsiDiri, pendidikan, pengalaman, keterampilan, peminatan)
        }
    }
    fun checkBiodata(userid:String){
        viewModelScope.launch{
            repository.checkBiodata(userid)
        }
    }
}