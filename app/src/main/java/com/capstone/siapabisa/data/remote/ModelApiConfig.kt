package com.capstone.siapabisa.data.remote

import com.capstone.siapabisa.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ModelApiConfig {

    companion object{
        private val loggingInterceptor = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        fun getApiService(): ModelApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://flask-model-ml-3vy5m7mmkq-et.a.run.app")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ModelApiService::class.java)
        }
    }
}