package com.hackvita.pathcare.hospitalDetail.model

import com.google.gson.annotations.SerializedName

data class HospitalResponseModel(

    @SerializedName("success"  ) var success  : Boolean?  = null,
    @SerializedName("hospital" ) var hospital : Hospital? = Hospital(),
    @SerializedName("message"  ) var message  : String?   = null,
    @SerializedName("eroor"    ) var eroor    : String?   = null
)

data class Hospital (

    @SerializedName("id"             ) var id            : String?                = null,
    @SerializedName("name"           ) var name          : String?                = null,
    @SerializedName("email"          ) var email         : String?                = null,
    @SerializedName("gst_in"         ) var gstIn         : String?                = null,
    @SerializedName("contact_number" ) var contactNumber : ArrayList<String>      = arrayListOf(),
    @SerializedName("address"        ) var address       : Address?               = Address(),
    @SerializedName("departments"    ) var departments   : ArrayList<Departments> = arrayListOf()

)

data class Address (

    @SerializedName("state" ) var state : String? = null,
    @SerializedName("city"  ) var city  : String? = null

)


data class Doctors (

    @SerializedName("doct_id" ) var doctId : String? = null,
    @SerializedName("_id"     ) var Id     : String? = null

)


data class Departments (

    @SerializedName("dept_name" ) var deptName : String?            = null,
    @SerializedName("doctors"   ) var doctors  : ArrayList<Doctors> = arrayListOf(),
    @SerializedName("_id"       ) var Id       : String?            = null

)