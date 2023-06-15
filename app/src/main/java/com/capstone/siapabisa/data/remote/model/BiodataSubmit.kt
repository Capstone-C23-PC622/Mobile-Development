package com.capstone.siapabisa.data.remote.model

import com.google.gson.annotations.SerializedName

data class BiodataSubmitParent(
    val data: BiodataSubmit
)

data class BiodataSubmit(

    @SerializedName("userId")
    val userId: String,

    @SerializedName("data")
    val data: BiodataSubmitData


)

data class BiodataSubmitData(

    @SerializedName("nama")
    val nama: String,

    @SerializedName("birthday")
    val birthday: Birthday,

    @SerializedName("alamat")
    val alamat: String,

    @SerializedName("deskripsiDiri")
    val deskripsiDiri:String,

    @SerializedName("pendidikan")
    val pendidikan: String,

    @SerializedName("pengalaman")
    val pengalaman: String,

    @SerializedName("peminatan")
    val peminatan: String,

    @SerializedName("keterampilan")
    val keterampilan: String
)