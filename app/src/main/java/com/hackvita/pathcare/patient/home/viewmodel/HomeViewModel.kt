package com.hackvita.pathcare.patient.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hackvita.pathcare.patient.home.model.HospitalRequestModel
import com.hackvita.pathcare.patient.home.model.HospitalResponseData
import com.hackvita.pathcare.patient.home.model.NearbyHospitalRequestModel
import com.hackvita.pathcare.patient.home.model.NearbyHospitalResponseData
import com.hackvita.pathcare.patient.home.repository.HomeRepository

class HomeViewModel : ViewModel() {
    private val repository= HomeRepository()
    val showProgress: LiveData<Boolean>
    val errorMessage: LiveData<String>
    val hospitalResponse :LiveData<HospitalResponseData>
    val nearbyHospitalResponse :LiveData<NearbyHospitalResponseData>

    init {
        this.showProgress = repository.showProgress
        this.errorMessage = repository.errorMessage
        this.hospitalResponse = repository.hospitalResponse
        this.nearbyHospitalResponse = repository.nearbyHospitalResponse
    }

    fun hospitalApiCall(token: String, hospitalRequestModel: HospitalRequestModel){
        repository.getHospitals(token, hospitalRequestModel)
    }
    fun nearbyHospitalApiCall(token: String, hospitalRequestModel: NearbyHospitalRequestModel){
        repository.getNearbyHospitals(token, hospitalRequestModel)
    }
}