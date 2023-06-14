package com.capstone.siapabisa.ui.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.remote.model.DetailLoker
import com.capstone.siapabisa.data.remote.model.Job

class DetailViewModel(private val repository: Repository): ViewModel() {

    fun getJob(id:String) : LiveData<Result<DetailLoker>> {

        return liveData {
            emit(Result.Loading)
            emitSource(repository.getJob(id))
        }
    }

}