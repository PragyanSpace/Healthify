package com.hackvita.pathcare.hospitalDetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hackvita.pathcare.hospitalDetail.model.AppointmentRequestModel
import com.hackvita.pathcare.hospitalDetail.model.AppointmentResponseModel
import com.hackvita.pathcare.hospitalDetail.model.HospitalResponseModel
import com.hackvita.pathcare.hospitalDetail.repository.HospitalDetailRepository

class HospitalDetailViewmodel(): ViewModel() {

    private val repository = HospitalDetailRepository()
    val showProgress: LiveData<Boolean>
    val errorMessage: LiveData<String>
    val hospitalResponseMutableLiveData: LiveData<HospitalResponseModel>
    val appointmentResponseMutableLiveData: LiveData<AppointmentResponseModel>


    init {
        this.showProgress = repository.showProgress
        this.errorMessage = repository.errorMessage
        this.hospitalResponseMutableLiveData = repository.hospitalResponseMutableLiveData
        this.appointmentResponseMutableLiveData = repository.appointmentResponseMutableLiveData
    }

    fun callHospitalDetail(token: String?, hospitalId: String) {
        repository.hospitalDetailApiCall(token, hospitalId)
    }

    fun callRequestAppointment(token: String?, appointmentRequestModel: AppointmentRequestModel) {
        repository.appointmentApiCall(token, appointmentRequestModel)
    }


}