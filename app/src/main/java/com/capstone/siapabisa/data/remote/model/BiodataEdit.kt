package com.capstone.siapabisa.data.remote.model

import com.google.gson.annotations.SerializedName

data class BiodataEdit(

    @SerializedName("")
    val data: BiodataEditData,

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

data class BiodataEditData(

    @SerializedName("nama")
    val nama: String,

    @SerializedName("birthday")
    val birthday: Birthday
)