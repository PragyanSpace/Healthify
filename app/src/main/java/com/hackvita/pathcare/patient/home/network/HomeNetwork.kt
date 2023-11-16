package com.hackvita.pathcare.patient.home.network

import com.hackvita.pathcare.patient.home.model.HospitalResponseData
import com.hackvita.pathcare.patient.home.model.NearbyHospitalResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface HomeNetwork  {

    @GET("api/v1/user/nearbyHospital")
    fun callNearbyHospitalApi(
        @Header("Authorization") Authorization: String?,
        @Query("latitude") latitude: String?,
        @Query("longitude") longitude: String?,
    ): Call<NearbyHospitalResponseData>

    @GET("api/v1/user/searchHospital")
    fun callHospitalApi(
        @Header("Authorization") Authorization: String?,
        @Query("city") city: String?,
        @Query("hospital_name") hospital: String?
    ): Call<HospitalResponseData>
}