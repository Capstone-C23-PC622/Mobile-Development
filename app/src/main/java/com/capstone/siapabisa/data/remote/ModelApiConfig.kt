package com.capstone.siapabisa.data.remote

import com.capstone.siapabisa.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ModelApiConfig {

    companion object {
        private val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS) // Set connect timeout to 60 seconds
            .readTimeout(60, TimeUnit.SECONDS) // Set read timeout to 60 seconds
            .writeTimeout(60, TimeUnit.SECONDS) // Set write timeout to 60 seconds
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