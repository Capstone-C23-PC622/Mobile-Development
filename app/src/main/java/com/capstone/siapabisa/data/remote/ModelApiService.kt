package com.capstone.siapabisa.data.remote

import com.capstone.siapabisa.data.remote.model.PredictRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ModelApiService{

    @POST("/predict")
    suspend fun getPrediction(
        @Body predictRequest: PredictRequest
    ) : Response<ResponsePredict>
}