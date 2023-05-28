package com.capstone.siapabisa.data.remote.model

import com.google.gson.annotations.SerializedName

data class Login(

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("token")
    val token: String? = null
)