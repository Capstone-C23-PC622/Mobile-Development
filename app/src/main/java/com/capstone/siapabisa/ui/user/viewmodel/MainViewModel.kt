package com.capstone.siapabisa.ui.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.data.remote.model.Job
import com.capstone.siapabisa.dummy.Jobs
import kotlinx.coroutines.launch
import com.capstone.siapabisa.data.Result
import com.capstone.siapabisa.data.remote.ResponseBiodata

class MainViewModel(private val repository: Repository): ViewModel() {

    fun getDummyJobs(): List<Jobs> = repository.getDummyJobs()

    private val _responseBiodata = repository.responseBiodata
    val responseBiodata: LiveData<Result<ResponseBiodata>> = _responseBiodata

    fun getBiodata(userid:String){
        viewModelScope.launch{
            repository.getBiodata(userid)
        }
    }
    fun getAllJobs() : LiveData<Result<List<Job>>> {

            return liveData {
                emit(Result.Loading)
                emitSource(repository.getAllJobs())
            }
        }
    }

