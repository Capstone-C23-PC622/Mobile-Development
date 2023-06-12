package com.capstone.siapabisa.data.remote

import com.capstone.siapabisa.data.remote.model.Biodata
import com.capstone.siapabisa.data.remote.model.Job
import com.capstone.siapabisa.data.remote.model.Login
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


data class ResponseRegister(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("error")
    val error: Boolean? = null
)

data class ResponseBiodata(

    @field:SerializedName("data")
    val biodata: Biodata? = null,

    @field:SerializedName("isVerified")
    val isVerified: Boolean? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)


data class ResponseSubmitBiodata(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class ResponseJobs(

    @field:SerializedName("data")
    val data: List<Job>,

    @field:SerializedName("isVerified")
    val isVerified: Boolean? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)


