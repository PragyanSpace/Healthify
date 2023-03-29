package com.hackvita.pathcare.register.model

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("name"             ) var name            : String?           = null,
    @SerializedName("email"            ) var email           : String?           = null,
    @SerializedName("password"         ) var password        : String?           = null,
    @SerializedName("phone_number"     ) var phoneNumber     : String?           = null,
    @SerializedName("gender"           ) var gender          : String?           = null,
    @SerializedName("_id"              ) var Id              : String?           = null,
    @SerializedName("prescriptions"    ) var prescriptions   : ArrayList<String> = arrayListOf(),
    @SerializedName("appointments"     ) var appointments    : ArrayList<String> = arrayListOf(),
    @SerializedName("req_appointments" ) var reqAppointments : ArrayList<String> = arrayListOf(),
    @SerializedName("__v"              ) var _v              : Int?              = null

)