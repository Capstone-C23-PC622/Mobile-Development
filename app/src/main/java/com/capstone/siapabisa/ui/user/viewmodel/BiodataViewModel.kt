package com.capstone.siapabisa.ui.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.data.remote.model.Birthday
import kotlinx.coroutines.launch

class BiodataViewModel(private val repository: Repository): ViewModel() {

    private val _responseSubmitBiodata = repository.responseSubmitBiodata
    val responseSubmitBiodata = _responseSubmitBiodata

    private val _responseEditBiodata = repository.responseEditBiodata
    val responseEditBiodata = _responseEditBiodata

    fun submitBiodata(userId:String, nama : String, birthday : Birthday, alamat : String, deskripsiDiri : String, pendidikan : String, pengalaman : String, keterampilan : String, peminatan : String){
        viewModelScope.launch {
            repository.submitBiodata(userId, nama, birthday, alamat, deskripsiDiri, pendidikan, pengalaman, keterampilan, peminatan)
        }
    }

    fun editBiodata(id:String, nama : String, birthday : Birthday, alamat : String, deskripsiDiri : String, pendidikan : String, pengalaman : String, keterampilan : String, peminatan : String){
        viewModelScope.launch {
            repository.editBiodata(id, nama, birthday, alamat, deskripsiDiri, pendidikan, pengalaman, keterampilan, peminatan)
        }
    }

}