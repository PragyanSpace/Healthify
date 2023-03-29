package com.hackvita.pathcare.register.model

import com.google.gson.annotations.SerializedName


data class RegisterRequestModel (
    @SerializedName("email") val email: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("password") val password: String?,

)