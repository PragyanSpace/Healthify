package com.hackvita.pathcare.login.model

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("address"          ) var address         : Address?                   = Address(),
    @SerializedName("_id"              ) var Id              : String?                    = null,
    @SerializedName("name"             ) var name            : String?                    = null,
    @SerializedName("email"            ) var email           : String?                    = null,
    @SerializedName("password"         ) var password        : String?                    = null,
    @SerializedName("dob"              ) var dob             : String?                    = null,
    @SerializedName("blood_group"      ) var bloodGroup      : String?                    = null,
    @SerializedName("prescriptions"    ) var prescriptions   : ArrayList<Prescriptions>   = arrayListOf(),
    @SerializedName("appointments"     ) var appointments    : ArrayList<Appointments>    = arrayListOf(),
    @SerializedName("req_appointments" ) var reqAppointments : ArrayList<ReqAppointments> = arrayListOf(),
    @SerializedName("__v"              ) var _v              : Int?                       = null,
    @SerializedName("gender"           ) var gender          : String?                    = null,
    @SerializedName("phone_number"     ) var phoneNumber     : String?                    = null
)

data class Appointments(
    @SerializedName("acpt_appointment") var acptAppointment: String? = null,
    @SerializedName("_id") var Id: String? = null
)

data class ReqAppointments(

    @SerializedName("req_appt") var reqAppt: String? = null,
    @SerializedName("_id") var Id: String? = null

)

data class Prescriptions (

    @SerializedName("prescription" ) var prescription : String? = null,
    @SerializedName("_id"          ) var Id           : String? = null

)

data class Address (

    @SerializedName("state"    ) var state    : String? = null,
    @SerializedName("city"     ) var city     : String? = null,
    @SerializedName("district" ) var district : String? = null,
    @SerializedName("pin_code" ) var pinCode  : String? = null

)