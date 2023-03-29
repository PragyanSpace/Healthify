package com.hackvita.pathcare.register.model


import com.google.gson.annotations.SerializedName


data class RegisterResponseModel (
@SerializedName("success" ) var success : Boolean? = null,
@SerializedName("user"    ) var user    : User?    = User(),
@SerializedName("token"   ) var token   : String?  = null
)