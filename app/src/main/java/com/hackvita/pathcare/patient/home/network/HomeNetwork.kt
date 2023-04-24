package com.hackvita.pathcare.patient.home.network

import com.hackvita.pathcare.patient.home.model.HospitalResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface HomeNetwork  {

    @GET("api/v1/user/searchHospital")
    fun callSearchHospitalApi(
        @Header("Authorization") Authorization: String?,
        @Query("city") city: String?,
        @Query("hospital") hospital: String?
    ): Call<HospitalResponseData>
}