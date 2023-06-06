package com.capstone.siapabisa.data.remote

import com.capstone.siapabisa.data.remote.model.Birthday
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username")username:String,
        @Field("password")password:String,
    ): Response<ResponseLogin>

    @FormUrlEncoded
    @POST("user/registrasi")
    suspend fun register(
        @Field("username")username:String,
        @Field("email")email:String,
        @Field("password")password:String,
        @Field("role")role:String
    ): Response<ResponseRegister>

    @FormUrlEncoded
    @POST("user/biodata")
    suspend fun submitBiodata(
        @Field("nama")nama:String,
        @Field("birthday")birthday: Birthday,
        @Field("alamat")alamat:String,
        @Field("deskripsiDiri")deskripsiDiri:String,
        @Field("pendidikan")pendidikan:String,
        @Field("pengalaman")pengalaman:String,
        @Field("keterampilan")keterampilan:String,
        @Field("peminatan")peminatan:String
    ) : Response<ResponseSubmitBiodata>


}