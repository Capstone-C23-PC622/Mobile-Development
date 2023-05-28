package com.capstone.siapabisa.di

import android.content.Context
import com.capstone.siapabisa.data.Repository
import com.capstone.siapabisa.data.remote.ApiConfig

object Injection {
    fun provideRepository(context: Context) : Repository {
        val apiService = ApiConfig.getApiService()
        return Repository(apiService, context)
    }
}