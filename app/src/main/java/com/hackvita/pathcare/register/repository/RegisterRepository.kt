package com.hackvita.pathcare.register.repository

import androidx.lifecycle.MutableLiveData
import com.hackvita.pathcare.RetrofitUtilClass
import com.hackvita.pathcare.register.model.RegisterRequestModel
import com.hackvita.pathcare.register.model.RegisterResponseModel
import com.hackvita.pathcare.register.network.RegisterService
import com.hackvita.pathcare.utility.AppUrls
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository() {

    val showProgress = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val registerResponseMutableLiveData = MutableLiveData<RegisterResponseModel>()

    fun registerApiCall(token: String?, registerRequestModel: RegisterRequestModel) {
        showProgress.value = true
        val client = RetrofitUtilClass.getRetrofit().create(RegisterService::class.java)
        var call = client?.registerApiCall(
            AppUrls.REGISTER_URL,
            token,
            registerRequestModel
        )
        call?.enqueue(object : Callback<RegisterResponseModel?> {
            override fun onResponse(
                call: Call<RegisterResponseModel?>,
                response: Response<RegisterResponseModel?>
            ) {
                showProgress.postValue(false)
                val body = response.body()

                if (response.isSuccessful) {
                    body?.let {
                        registerResponseMutableLiveData.postValue(body)
                    }
                }
                else{
                }
            }

            override fun onFailure(call: Call<RegisterResponseModel?>, t: Throwable) {
                showProgress.postValue(false)
                errorMessage.postValue("Server error please try after sometime")
            }
        })
    }
}