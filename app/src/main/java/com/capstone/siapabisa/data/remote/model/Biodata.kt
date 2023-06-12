package com.capstone.siapabisa.data.remote.model

import com.google.gson.annotations.SerializedName

data class Biodata(

    @field:SerializedName("birthday")
    val birthday: Birthday? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("pendidikan")
    val pendidikan: String? = null,

    @field:SerializedName("deskripsiDiri")
    val deskripsiDiri: String? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("pengalaman")
    val pengalaman: String? = null,

    @field:SerializedName("peminatan")
    val peminatan: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("keterampilan")
    val keterampilan: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)