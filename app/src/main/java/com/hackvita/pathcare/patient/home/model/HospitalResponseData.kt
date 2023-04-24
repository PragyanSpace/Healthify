package com.hackvita.pathcare.patient.home.model

import com.google.gson.annotations.SerializedName

data class HospitalResponseData (
    @SerializedName("success"     ) var success     : Boolean?               = null,
    @SerializedName("resHospital" ) var resHospital : ArrayList<ResHospital> = arrayListOf()
)

data class ResHospital (

    @SerializedName("_id"     ) var Id      : String?  = null,
    @SerializedName("name"    ) var name    : String?  = null,
    @SerializedName("address" ) var address : Address? = Address()
    )

data class Address(
    @SerializedName("state"    ) var state    : String? = null,
    @SerializedName("city"     ) var city     : String? = null,
    @SerializedName("district" ) var district : String? = null,
    @SerializedName("pin_code" ) var pinCode  : Int?    = null
)
