package com.hackvita.pathcare.patient.home.model

import com.google.gson.annotations.SerializedName


data class HospitalRequestModel (
    @SerializedName("city") var city : String?,
    @SerializedName("hospital") var hospital : String?
)