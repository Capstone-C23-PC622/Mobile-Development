package com.capstone.siapabisa.data.local

import com.capstone.siapabisa.data.remote.User
import com.google.gson.annotations.SerializedName

data class Login(

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("token")
    val token: String? = null
)