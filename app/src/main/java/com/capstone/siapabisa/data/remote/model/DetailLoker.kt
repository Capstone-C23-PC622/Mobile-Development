package com.capstone.siapabisa.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailLoker(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("pendidikan")
    val pendidikan: String? = null,

    @field:SerializedName("namaPerusahaan")
    val namaPerusahaan: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("lowongan")
    val lowongan: String? = null,

    @field:SerializedName("lokasi")
    val lokasi: String? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("jenisLowongan")
    val jenisLowongan: String? = null,

    @field:SerializedName("pengalaman")
    val pengalaman: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
: Parcelable