package com.capstone.siapabisa.ui.user.viewmodel

import androidx.lifecycle.ViewModel
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.dummy.Jobs

class MainViewModel(private val repository: Repository): ViewModel() {

    fun getDummyJobs(): List<Jobs> = repository.getDummyJobs()

}