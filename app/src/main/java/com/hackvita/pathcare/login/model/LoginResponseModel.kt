package com.hackvita.pathcare.login.model


import com.google.gson.annotations.SerializedName


data class LoginResponseModel(

    @SerializedName("data") var data: Data? = null,
    @SerializedName("status_code") var status_code: Int? = null,
    @SerializedName("message") var message: String? = null

)