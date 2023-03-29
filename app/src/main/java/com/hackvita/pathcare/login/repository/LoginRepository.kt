package com.hackvita.pathcare.login.repository

import androidx.lifecycle.MutableLiveData
import com.hackvita.pathcare.RetrofitUtilClass
import com.hackvita.pathcare.login.model.LoginRequestModel
import com.hackvita.pathcare.login.model.LoginResponseModel
import com.hackvita.pathcare.login.network.LoginService
import com.hackvita.pathcare.utility.AppUrls
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository() {

    val showProgress = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val loginResponseMutableLiveData = MutableLiveData<LoginResponseModel>()

    fun loginApiCall(token: String?, loginRequestModel: LoginRequestModel) {
        showProgress.value = true
        val client = RetrofitUtilClass.getRetrofit()?.create(LoginService::class.java)
        var call = client?.loginApiCall(AppUrls.LOGIN_URL, token, loginRequestModel)
        call?.enqueue(object : Callback<LoginResponseModel?> {
            override fun onResponse(
                call: Call<LoginResponseModel?>,
                response: Response<LoginResponseModel?>
            ) {
                showProgress.postValue(false)
                val body = response.body()

                if (response.isSuccessful) {
                    body?.let {
                        loginResponseMutableLiveData.postValue(body)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<LoginResponseModel?>, t: Throwable) {
                showProgress.postValue(false)
                errorMessage.postValue("Server error please try after sometime")
            }
        })
    }

}