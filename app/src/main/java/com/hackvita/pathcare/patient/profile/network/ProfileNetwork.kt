package com.hackvita.pathcare.patient.profile.network

import com.hackvita.pathcare.patient.profile.model.UserDataResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface ProfileNetwork  {

    @GET
    fun callUserDataApi(
        @Header("Authorization") Authorization: String?,
        @Url url:String
    ): Call<UserDataResponseModel>
}