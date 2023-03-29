package com.hackvita.pathcare.login.model

import com.google.gson.annotations.SerializedName

data class Data (

    @SerializedName("token") var token : String?=null,
    @SerializedName("error") var error : String?=null

)