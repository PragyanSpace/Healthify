package com.hackvita.pathcare.hospitalDetail.model

import com.google.gson.annotations.SerializedName

data class AppointmentRequestModel(
    @SerializedName("h_id"             ) var hId             : String? = null,
    @SerializedName("u_id"             ) var uId             : String? = null,
    @SerializedName("appointment_date" ) var appointmentDate : String? = null
)
