package com.capstone.siapabisa.data.remote

import com.capstone.siapabisa.data.remote.model.Biodata
import com.capstone.siapabisa.data.remote.model.DetailLoker
import com.capstone.siapabisa.data.remote.model.Job
import com.capstone.siapabisa.data.remote.model.Login
import com.capstone.siapabisa.data.remote.model.ProfilUsaha
import com.capstone.siapabisa.data.remote.model.UpdatedBiodata
import com.capstone.siapabisa.data.remote.model.UpdatedProfil
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

data class ResponseEditBiodata(

    @field:SerializedName("isVerified")
    val isVerified: Boolean? = null,

    @field:SerializedName("updateData")
    val updateData: UpdatedBiodata? = null,

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

data class ResponseDetail(

    @field:SerializedName("data")
    val data: DetailLoker,

    @field:SerializedName("isVerified")
    val isVerified: Boolean? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class ResponseProfilUsaha(

    @field:SerializedName("data")
    val data: ProfilUsaha? = null,

    @field:SerializedName("isVerified")
    val isVerified: Boolean? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class ResponseSubmitProfil(

    @field:SerializedName("isEmpty")
    val isEmpty: Boolean? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class ResponseEditProfil(

    @field:SerializedName("isVerified")
    val isVerified: Boolean? = null,

    @field:SerializedName("updateData")
    val updateData: UpdatedProfil? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class ResponsePostLoker(

    @field:SerializedName("isEmpty")
    val isEmpty: Boolean? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class ResponsePredict(

    @field:SerializedName("result")
    val result: String? = null
)



