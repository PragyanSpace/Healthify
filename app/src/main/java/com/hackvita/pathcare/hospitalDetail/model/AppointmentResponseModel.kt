package com.hackvita.pathcare.hospitalDetail.model

import com.google.gson.annotations.SerializedName

data class AppointmentResponseModel(
    @SerializedName("success"     ) var success     : Boolean?     = null,
    @SerializedName("appointment" ) var appointment : Appointment? = Appointment(),
    @SerializedName("message"     ) var message     : String?      = null
)

data class Appointment(
    @SerializedName("hospital_id"      ) var hospitalId      : String? = null,
    @SerializedName("user_id"          ) var userId          : String? = null,
    @SerializedName("appointment_date" ) var appointmentDate : String? = null,
    @SerializedName("urgency"          ) var urgency         : Int?    = null,
    @SerializedName("_id"              ) var Id              : String? = null,
    @SerializedName("req_date"         ) var reqDate         : String? = null,
    @SerializedName("__v"              ) var _v              : Int?    = null
)
