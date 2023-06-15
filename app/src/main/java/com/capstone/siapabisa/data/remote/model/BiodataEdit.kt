package com.capstone.siapabisa.data.remote.model

data class BiodataEdit(

    val userId: String,

    val data: BiodataEditData,

    val alamat: String,

    val deskripsiDiri:String,

    val pendidikan: String,

    val pengalaman: String,

    val peminatan: String,

    val keterampilan: String
)

data class BiodataEditData(

    val nama: String,

    val birthday: Birthday
)