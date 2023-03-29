package com.hackvita.pathcare.register.network

import com.hackvita.pathcare.register.model.RegisterRequestModel
import com.hackvita.pathcare.register.model.RegisterResponseModel
import retrofit2.Call
import retrofit2.http.*

interface RegisterService
{
    @POST
    fun registerApiCall(
        @Url url: String,
        @Header("Authorization") Authorization: String?,
        @Body registerRequestModel: RegisterRequestModel
    ): Call<RegisterResponseModel>
}
