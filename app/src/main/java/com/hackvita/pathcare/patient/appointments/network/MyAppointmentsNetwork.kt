package com.hackvita.pathcare.patient.appointments.network

import com.hackvita.pathcare.patient.appointments.model.MyAppointmentsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MyAppointmentsNetwork {

        @GET("api/v1/user/acceptedApt")
        fun callMyAppointmentsApi(
            @Header("Authorization") Authorization: String?,
        ): Call<MyAppointmentsModel>
}