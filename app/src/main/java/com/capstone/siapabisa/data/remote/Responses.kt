package com.capstone.siapabisa.data.remote

import com.capstone.siapabisa.data.local.Login
import com.google.gson.annotations.SerializedName


data class ResponseLogin(

    @field:SerializedName("data")
    val data: Login? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class User(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("role")
    val role: Int? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)


data class ResponseRegister(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("error")
    val error: Boolean? = null
)

data class ResponseSubmitBiodata(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

