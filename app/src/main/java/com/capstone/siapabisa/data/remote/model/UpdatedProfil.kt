package com.capstone.siapabisa.data.remote.model

import com.google.gson.annotations.SerializedName

data class UpdatedProfil(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("bidangUsaha")
    val bidangUsaha: String? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("deskripsiUsaha")
    val deskripsiUsaha: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("namaUsaha")
    val namaUsaha: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
