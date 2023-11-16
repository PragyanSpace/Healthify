package com.hackvita.pathcare.login.model


import com.google.gson.annotations.SerializedName


data class LoginResponseModel(
    @SerializedName("success" ) var success : Boolean? = null,
    @SerializedName("id"      ) var id      : String?  = null,
    @SerializedName("token"   ) var token   : String?  = null

)