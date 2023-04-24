package com.hackvita.pathcare.patient.profile.repository

import androidx.lifecycle.MutableLiveData
import com.hackvita.pathcare.RetrofitUtilClass
import com.hackvita.pathcare.patient.profile.model.UserDataResponseModel
import com.hackvita.pathcare.patient.profile.network.ProfileNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRepository() {

    val showProgress = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val userResponseMutableLiveData = MutableLiveData<UserDataResponseModel>()

    fun loginApiCall(token: String?, id: String?) {
        showProgress.value = true
        val client = RetrofitUtilClass.getRetrofit()?.create(ProfileNetwork::class.java)
        val url="api/v1/user/details/$id"
        var call = client?.callUserDataApi(token,url)
        call?.enqueue(object : Callback<UserDataResponseModel?> {
            override fun onResponse(
                call: Call<UserDataResponseModel?>,
                response: Response<UserDataResponseModel?>
            ) {
                showProgress.postValue(false)
                val body = response.body()

                if (response.isSuccessful) {
                    body?.let {
                        userResponseMutableLiveData.postValue(body)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<UserDataResponseModel?>, t: Throwable) {
                showProgress.postValue(false)
                errorMessage.postValue("Server error please try after sometime")
            }
        })
    }

}