package com.capstone.siapabisa.data.remote.model

import com.google.gson.annotations.SerializedName

data class Birthday(
    @SerializedName("day")
    val day: Int? = null,
    @SerializedName("month")
    val month: Int? = null,
    @SerializedName("year")
    val year: Int? = null
)