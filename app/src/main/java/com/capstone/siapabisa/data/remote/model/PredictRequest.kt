package com.capstone.siapabisa.data.remote.model

import com.google.gson.annotations.SerializedName

data class PredictRequest(
    @field:SerializedName("keterampilan")
    val keterampilan: String? = null,

    @field:SerializedName("peminatan")
    val peminatan: String? = null,

)