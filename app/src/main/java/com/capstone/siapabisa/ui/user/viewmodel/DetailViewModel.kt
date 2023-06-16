package com.capstone.siapabisa.ui.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.remote.ResponseDelete
import com.capstone.siapabisa.data.remote.model.DetailLoker
import com.capstone.siapabisa.data.remote.model.Job
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository): ViewModel() {

    private val _responseDeleteLoker = repository.responseDelete
    val responseDeleteLoker: LiveData<Result<ResponseDelete>> = _responseDeleteLoker

    fun getJob(id:String) : LiveData<Result<DetailLoker>> {

        return liveData {
            emit(Result.Loading)
            emitSource(repository.getJob(id))
        }
    }

    fun deleteLoker(id:String){
        viewModelScope.launch{
            repository.deleteLoker(id)
        }
    }

}