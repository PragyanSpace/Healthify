package com.hackvita.pathcare.patient.profile.model

import com.google.gson.annotations.SerializedName

data class UserDataResponseModel(

    @SerializedName("success" ) var success : Boolean? = null,
    @SerializedName("user"    ) var user    : User?    = User(),
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("error"   ) var error   : String?  = null
)

data class User(

    @SerializedName("id"           ) var id          : String?  = null,
    @SerializedName("name"         ) var name        : String?  = null,
    @SerializedName("email"        ) var email       : String?  = null,
    @SerializedName("dob"          ) var dob         : String?  = null,
    @SerializedName("blood_group"  ) var bloodGroup  : String?  = null,
    @SerializedName("phone_number" ) var phoneNumber : String?  = null,
    @SerializedName("gender"       ) var gender      : String?  = null,
    @SerializedName("address"      ) var address     : Address? = Address()

)

data class Address(
    @SerializedName("state"    ) var state    : String? = null,
    @SerializedName("city"     ) var city     : String? = null,
    @SerializedName("district" ) var district : String? = null,
    @SerializedName("pin_code" ) var pinCode  : String? = null
)
