package com.hackvita.pathcare.login.model

import com.google.gson.annotations.SerializedName


data class LoginRequestModel (
    @SerializedName("phone_number") var email : Long?,
    @SerializedName("password") var password : String?

)