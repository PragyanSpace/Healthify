package com.hackvita.pathcare.patient.home.model

import com.google.gson.annotations.SerializedName

data class NearbyHospitalResponseData(
    @SerializedName("success"   ) var success   : Boolean?             = null,
    @SerializedName("hospitals" ) var hospitals : ArrayList<Hospitals> = arrayListOf(),
    @SerializedName("message"   ) var message   : String?              = null,
    @SerializedName("error"     ) var error     : String?              = null
)

data class HosAddress (

    @SerializedName("state" ) var state : String? = null,
    @SerializedName("city"  ) var city  : String? = null

)

data class Hospitals (

    @SerializedName("address" ) var address : HosAddress? = HosAddress(),
    @SerializedName("_id"     ) var Id      : String?  = null,
    @SerializedName("name"    ) var name    : String?  = null,
    @SerializedName("contact_number" ) var contactNumber : ArrayList<String> = arrayListOf()

)
