package com.hackvita.pathcare.patient.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hackvita.pathcare.patient.home.model.HospitalRequestModel
import com.hackvita.pathcare.patient.home.model.HospitalResponseData
import com.hackvita.pathcare.patient.home.repository.HomeRepository

class HomeViewModel : ViewModel() {
    private val repository= HomeRepository()
    val showProgress: LiveData<Boolean>
    val errorMessage: LiveData<String>
    val hospitalResponse :LiveData<HospitalResponseData>

    init {
        this.showProgress = repository.showProgress
        this.errorMessage = repository.errorMessage
        this.hospitalResponse = repository.hospitalResponse
    }

    fun hospitalApiCall(token: String, hospitalRequestModel: HospitalRequestModel){
        repository.getHospitals(token, hospitalRequestModel)
    }
}