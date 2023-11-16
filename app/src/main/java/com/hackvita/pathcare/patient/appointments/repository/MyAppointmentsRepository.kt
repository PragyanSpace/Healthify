package com.hackvita.pathcare.patient.appointments.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hackvita.pathcare.RetrofitUtilClass
import com.hackvita.pathcare.patient.appointments.model.MyAppointmentsModel
import com.hackvita.pathcare.patient.appointments.network.MyAppointmentsNetwork
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAppointmentsRepository {
        val showProgress = MutableLiveData<Boolean>()
        val errorMessage = MutableLiveData<String>()
        val appointmentResponse = MutableLiveData<MyAppointmentsModel>()

        fun getMyAppointments(token: String) {
            var call: Call<MyAppointmentsModel>?

            val client = RetrofitUtilClass.getRetrofit()?.create(MyAppointmentsNetwork::class.java)

            call =
                client?.callMyAppointmentsApi(token)

            call?.enqueue(object : Callback<MyAppointmentsModel?> {
                override fun onResponse(
                    call: Call<MyAppointmentsModel?>,
                    response: Response<MyAppointmentsModel?>
                ) {
                    showProgress.postValue(false)
                    val body = response.body()
                    if (response.isSuccessful) {
                        body?.let {
                            appointmentResponse.postValue(it)
                        }
                    } else {
                        val jObjError = JSONObject(response.errorBody()?.string())
                        errorMessage.postValue(jObjError.getString("message"))
                    }
                }

                override fun onFailure(call: Call<MyAppointmentsModel?>, t: Throwable) {
                    showProgress.postValue(false)
                    errorMessage.postValue("Server error please try after sometime")
                    Log.i("ErrorCheck", "onFailure: ${t.message} ")
                }
            })
        }

}