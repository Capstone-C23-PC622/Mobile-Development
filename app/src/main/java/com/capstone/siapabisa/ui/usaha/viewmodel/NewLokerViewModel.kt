package com.capstone.siapabisa.ui.usaha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.data.remote.ResponsePostLoker
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.remote.ResponseProfilUsaha
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class NewLokerViewModel(private val repository: Repository): ViewModel() {

    val _responsePostLoker = repository.responseUpload
    val responsePostLoker : LiveData<Result<ResponsePostLoker>> = _responsePostLoker

    private val _responseProfilUsaha = repository.responseProfilUsaha
    val responseProfilUsaha: LiveData<Result<ResponseProfilUsaha>> = _responseProfilUsaha

    fun postLoker(userId: RequestBody, namaPerusahaan: RequestBody, lowongan: RequestBody, jenisLowongan: RequestBody, pendidikan: RequestBody, pengalaman: RequestBody, lokasi: RequestBody, deskripsi: RequestBody, image: MultipartBody.Part){
        viewModelScope.launch{
            repository.postLoker(userId, namaPerusahaan, lowongan, jenisLowongan, pendidikan, pengalaman, lokasi, deskripsi, image)
        }
    }

    fun getProfilUsaha(userid:String){
        viewModelScope.launch{
            repository.getProfilUsaha(userid)
        }
    }
}