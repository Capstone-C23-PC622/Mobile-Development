package com.capstone.siapabisa.data.remote

import com.capstone.siapabisa.data.remote.model.BiodataEdit
import com.capstone.siapabisa.data.remote.model.BiodataSubmit
import com.capstone.siapabisa.data.remote.model.BiodataSubmitParent
import com.capstone.siapabisa.data.remote.model.Birthday
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Url


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

    @GET("/user/biodata/{userid}")
    suspend fun getBiodata(
        @Path("userid")userid:String
    ): Response<ResponseBiodata>

    @POST("/user/biodata")
    suspend fun submitBiodata(
        @Body biodata: BiodataSubmit
    ): Response<ResponseSubmitBiodata>

    @PUT("/user/biodata/{id}")
    suspend fun editBiodata(
        @Path("id")id:String,
        @Body biodata: BiodataEdit
    ): Response<ResponseEditBiodata>

    @GET("pengusaha/loker")
    suspend fun getAllJobs(): Response<ResponseJobs>

    @GET("pengusaha/loker/{id}")
    suspend fun getJob(
        @Path("id")id:String
    ): Response<ResponseDetail>

    @GET("pengusaha/profile/{id}")
    suspend fun getProfilUsaha(
        @Path("id")id:String
    ): Response<ResponseProfilUsaha>

    @FormUrlEncoded
    @POST("pengusaha/profile")
    suspend fun submitProfilUsaha(
        @Field("userId")userId:String,
        @Field("namaUsaha")namaUsaha:String,
        @Field("alamat")alamat:String,
        @Field("deskripsiUsaha")deskripsiUsaha:String,
        @Field("bidangUsaha")bidangUsaha:String,
    ): Response<ResponseSubmitProfil>

    @FormUrlEncoded
    @PUT("pengusaha/profile/{id}")
    suspend fun updateProfilUsaha(
        @Path("id")id:String,
        @Field("userId")userId:String,
        @Field("namaUsaha")namaUsaha:String,
        @Field("alamat")alamat:String,
        @Field("deskripsiUsaha")deskripsiUsaha:String,
        @Field("bidangUsaha")bidangUsaha:String,
    ): Response<ResponseEditProfil>

    @Multipart
    @POST("pengusaha/loker")
    suspend fun postLoker(
        @Part("userId")userid: RequestBody,
        @Part("namaPerusahaan")namaUsaha:RequestBody,
        @Part("lowongan")lowongan:RequestBody,
        @Part("jenisLowongan")jenisLowongan:RequestBody,
        @Part("pendidikan")pendidikan:RequestBody,
        @Part("pengalaman")pengalaman:RequestBody,
        @Part("lokasi")lokasi:RequestBody,
        @Part("deskripsi")deskripsi:RequestBody,
        @Part file: MultipartBody.Part

    ): Response<ResponsePostLoker>
}