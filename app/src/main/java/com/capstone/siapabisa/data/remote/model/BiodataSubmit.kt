package com.capstone.siapabisa.data.remote.model

import com.google.gson.annotations.SerializedName

data class BiodataSubmit(

    val userId: String,

    val data: BiodataSubmitData,

    val alamat: String,

    val deskripsiDiri:String,

    val pendidikan: String,

    val pengalaman: String,

    val peminatan: String,

    val keterampilan: String
)

data class BiodataSubmitData(

    val nama: String,

    val birthday: Birthday
)