package com.hackvita.pathcare.hospitalDetail.repository

import androidx.lifecycle.MutableLiveData
import com.hackvita.pathcare.RetrofitUtilClass
import com.hackvita.pathcare.hospitalDetail.model.Appointment
import com.hackvita.pathcare.hospitalDetail.model.AppointmentRequestModel
import com.hackvita.pathcare.hospitalDetail.model.AppointmentResponseModel
import com.hackvita.pathcare.hospitalDetail.model.HospitalResponseModel
import com.hackvita.pathcare.hospitalDetail.network.HospitalDetailService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HospitalDetailRepository {

    val showProgress = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val hospitalResponseMutableLiveData = MutableLiveData<HospitalResponseModel>()
    val appointmentResponseMutableLiveData = MutableLiveData<AppointmentResponseModel>()

    fun hospitalDetailApiCall(token: String?, hospitalId: String) {
        showProgress.value = true
        val client = RetrofitUtilClass.getRetrofit()?.create(HospitalDetailService::class.java)
        val url="api/v1/hospital/detail/$hospitalId"
        var call = client?.hospitalDetailApiCall(token, url)
        call?.enqueue(object : Callback<HospitalResponseModel?> {
            override fun onResponse(
                call: Call<HospitalResponseModel?>,
                response: Response<HospitalResponseModel?>
            ) {
                showProgress.postValue(false)
                val body = response.body()

                if (response.isSuccessful) {
                    body?.let {
                        hospitalResponseMutableLiveData.postValue(body)
                    }
                } else {

                }

            }

            override fun onFailure(call: Call<HospitalResponseModel?>, t: Throwable) {

            }
        })
    }

    fun appointmentApiCall(token: String?, appointmentRequestModel: AppointmentRequestModel) {
        showProgress.value = true
        val client = RetrofitUtilClass.getRetrofit()?.create(HospitalDetailService::class.java)
        var call = client?.requestAppointmentApiCall(token, appointmentRequestModel)
        call?.enqueue(object : Callback<AppointmentResponseModel?> {
            override fun onResponse(
                call: Call<AppointmentResponseModel?>,
                response: Response<AppointmentResponseModel?>
            ) {
                showProgress.postValue(false)
                val body = response.body()

                if (response.isSuccessful) {
                    body?.let {
                        appointmentResponseMutableLiveData.postValue(body)
                    }
                } else {

                }

            }

            override fun onFailure(call: Call<AppointmentResponseModel?>, t: Throwable) {

            }
        })
    }

}