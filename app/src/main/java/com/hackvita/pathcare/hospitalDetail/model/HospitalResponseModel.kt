package com.hackvita.pathcare.hospitalDetail.model

import com.google.gson.annotations.SerializedName

data class HospitalResponseModel(

    @SerializedName("success") var success: Boolean? = null,
    @SerializedName("hospital") var hospital: Hospital? = Hospital()
)

data class Hospital(

    @SerializedName("id"             ) var id            : String?           = null,
    @SerializedName("name"           ) var name          : String?           = null,
    @SerializedName("email"          ) var email         : String?           = null,
    @SerializedName("contact_number" ) var contactNumber : ArrayList<String> = arrayListOf(),
    @SerializedName("address"        ) var address       : Address?          = Address(),
    @SerializedName("departments"    ) var departments   : ArrayList<String> = arrayListOf()
)

data class Address(
    @SerializedName("city" ) var city : String? = null
)