package com.hackvita.pathcare.patient.appointments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hackvita.pathcare.patient.appointments.model.MyAppointmentsModel
import com.hackvita.pathcare.patient.appointments.repository.MyAppointmentsRepository

class MyAppointmentsViewmodel:ViewModel() {
        private val repository= MyAppointmentsRepository()
        val showProgress: LiveData<Boolean>
        val errorMessage: LiveData<String>
        val hospitalResponse : LiveData<MyAppointmentsModel>

        init {
            this.showProgress = repository.showProgress
            this.errorMessage = repository.errorMessage
            this.hospitalResponse = repository.appointmentResponse
        }

        fun myAppointmentsApiCall(token: String){
            repository.getMyAppointments(token)
        }
}