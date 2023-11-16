package com.hackvita.pathcare.patient.home.model

import com.google.gson.annotations.SerializedName

data class NearbyHospitalRequestModel(
    @SerializedName("latitude") var latitude : String?,
    @SerializedName("longitude") var longitude : String?
)
