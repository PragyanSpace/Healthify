package com.hackvita.pathcare.login.network

import com.hackvita.pathcare.login.model.LoginRequestModel
import com.hackvita.pathcare.login.model.LoginResponseModel
import retrofit2.Call
import retrofit2.http.*

interface LoginService  {
    @POST
    fun loginApiCall(
        @Url url: String,
        @Header("Authorization") Authorization: String?,
        @Body loginRequestModel: LoginRequestModel
    ): Call<LoginResponseModel>

}
