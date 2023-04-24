package com.hackvita.pathcare.patient.home.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hackvita.pathcare.RetrofitUtilClass
import com.hackvita.pathcare.patient.home.model.HospitalRequestModel
import com.hackvita.pathcare.patient.home.model.HospitalResponseData
import com.hackvita.pathcare.patient.home.network.HomeNetwork
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {
    val showProgress = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val hospitalResponse = MutableLiveData<HospitalResponseData>()

    fun getHospitals(token: String, hospitalRequestModel: HospitalRequestModel) {

        val client = RetrofitUtilClass.getRetrofit()?.create(HomeNetwork::class.java)

        val call = client?.callSearchHospitalApi(token, hospitalRequestModel.city,hospitalRequestModel.hospital)

        call?.enqueue(object : Callback<HospitalResponseData?> {
            override fun onResponse(
                call: Call<HospitalResponseData?>,
                response: Response<HospitalResponseData?>
            ) {
                showProgress.postValue(false)
                val body = response.body()
                if (response.isSuccessful) {
                    body?.let {
                        hospitalResponse.postValue(it)
                    }
                } else {
                    val jObjError = JSONObject(response.errorBody()?.string())
                    errorMessage.postValue(jObjError.getString("message"))
                }
            }

            override fun onFailure(call: Call<HospitalResponseData?>, t: Throwable) {
                showProgress.postValue(false)
                errorMessage.postValue("Server error please try after sometime")
                Log.i("ErrorCheck", "onFailure: ${t.message} ")
            }
        })


    }
}