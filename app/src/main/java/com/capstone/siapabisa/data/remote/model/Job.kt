package com.capstone.siapabisa.data.remote.model

import com.google.gson.annotations.SerializedName

data class Job(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("pendidikan")
    val pendidikan: String? = null,

    @field:SerializedName("lokasi")
    val lokasi: String? = null,

    @field:SerializedName("namaPerusahaan")
    val namaPerusahaan: String? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("jenisLowongan")
    val jenisLowongan: String? = null,

    @field:SerializedName("pengalaman")
    val pengalaman: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)