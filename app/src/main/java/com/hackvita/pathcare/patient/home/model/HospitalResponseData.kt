package com.hackvita.pathcare.patient.home.model

import com.google.gson.annotations.SerializedName

data class HospitalResponseData (
    @SerializedName("success"   ) var success   : Boolean?             = null,
    @SerializedName("hospitals" ) var hospitals : ArrayList<SearchedHospitals> = arrayListOf(),
    @SerializedName("message"   ) var message   : String?              = null,
    @SerializedName("error"     ) var error     : String?              = null
)

data class SearchedHospitals (

    @SerializedName("address" ) var address : Address? = Address(),
    @SerializedName("_id"     ) var Id      : String?  = null,
    @SerializedName("name"    ) var name    : String?  = null

)

data class Address (

    @SerializedName("state" ) var state : String? = null,
    @SerializedName("city"  ) var city  : String? = null

)
