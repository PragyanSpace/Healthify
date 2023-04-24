package com.hackvita.pathcare.hospitalDetail.network

import com.hackvita.pathcare.hospitalDetail.model.AppointmentRequestModel
import com.hackvita.pathcare.hospitalDetail.model.AppointmentResponseModel
import com.hackvita.pathcare.hospitalDetail.model.HospitalResponseModel
import retrofit2.Call
import retrofit2.http.*

interface HospitalDetailService {
    @GET
    fun hospitalDetailApiCall(
        @Header("Authorization") Authorization: String?,
        @Url url: String
    ): Call<HospitalResponseModel>

    @POST("api/v1/user/reqApt")
    fun requestAppointmentApiCall(
        @Header("Authorization") Authorization: String?,
        @Body appointmentRequestModel: AppointmentRequestModel
    ): Call<AppointmentResponseModel>

}
