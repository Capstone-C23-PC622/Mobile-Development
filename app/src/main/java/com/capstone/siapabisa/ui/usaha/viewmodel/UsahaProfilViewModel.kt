package com.capstone.siapabisa.ui.usaha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.data.remote.model.Birthday
import kotlinx.coroutines.launch

class UsahaProfilViewModel(private val repository: Repository): ViewModel() {

    private val _responseSubmitProfile = repository.responseSubmitProfil
    val responseSubmitProfile = _responseSubmitProfile

    private val _responseEditProfile = repository.responseEditProfil
    val responseEditProfile = _responseEditProfile

   fun submitProfilUsaha(userId:String, namaUsaha:String, alamat:String, deskripsi:String, bidangUsaha:String){
       viewModelScope.launch{
           repository.submitProfilUsaha(userId, namaUsaha, alamat, deskripsi, bidangUsaha)
       }
   }

    fun editProfilUsaha(id:String, userId:String,namaUsaha:String,alamat:String,deskripsi:String,bidangUsaha:String){
        viewModelScope.launch{
            repository.editProfilUsaha(id, userId, namaUsaha, alamat, deskripsi, bidangUsaha)
        }
    }
}