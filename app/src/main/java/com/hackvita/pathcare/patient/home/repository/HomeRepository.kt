package com.hackvita.pathcare.patient.home.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hackvita.pathcare.RetrofitUtilClass
import com.hackvita.pathcare.patient.home.model.HospitalRequestModel
import com.hackvita.pathcare.patient.home.model.HospitalResponseData
import com.hackvita.pathcare.patient.home.model.NearbyHospitalRequestModel
import com.hackvita.pathcare.patient.home.model.NearbyHospitalResponseData
import com.hackvita.pathcare.patient.home.network.HomeNetwork
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {
    val showProgress = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val hospitalResponse = MutableLiveData<HospitalResponseData>()
    val nearbyHospitalResponse = MutableLiveData<NearbyHospitalResponseData>()

    fun getHospitals(token: String, hospitalRequestModel: HospitalRequestModel) {
        var call: Call<HospitalResponseData>?

        val client = RetrofitUtilClass.getRetrofit()?.create(HomeNetwork::class.java)

        call =
            client?.callHospitalApi(token, hospitalRequestModel.city, hospitalRequestModel.hospital)

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

        fun getNearbyHospitals(token: String, hospitalRequestModel: NearbyHospitalRequestModel) {
            showProgress.value = true
            var call: Call<NearbyHospitalResponseData>?

            val client = RetrofitUtilClass.getRetrofit()?.create(HomeNetwork::class.java)

            call=client?.callNearbyHospitalApi(
                token,
                hospitalRequestModel.latitude,
                hospitalRequestModel.longitude
            )

            call?.enqueue(object : Callback<NearbyHospitalResponseData?> {
                override fun onResponse(
                    call: Call<NearbyHospitalResponseData?>,
                    response: Response<NearbyHospitalResponseData?>
                ) {
                    showProgress.postValue(false)
                    val body = response.body()
                    if (response.isSuccessful) {
                        body?.let {
                            nearbyHospitalResponse.postValue(it)
                        }
                    } else {
                        val jObjError = JSONObject(response.errorBody()?.string())
                        errorMessage.postValue(jObjError.getString("message"))
                    }
                }

                override fun onFailure(call: Call<NearbyHospitalResponseData?>, t: Throwable) {
                    showProgress.postValue(false)
                    errorMessage.postValue("Server error please try after sometime")
                    Log.i("ErrorCheck", "onFailure: ${t.message} ")
                }
            })


        }
    }