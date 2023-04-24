package com.hackvita.pathcare.patient.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hackvita.pathcare.patient.profile.model.UserDataResponseModel
import com.hackvita.pathcare.patient.profile.repository.ProfileRepository

class ProfileViewmodel(): ViewModel() {

    private val repository = ProfileRepository()
    val showProgress: LiveData<Boolean>
    val errorMessage: LiveData<String>
    val userResponseMutableLiveData: LiveData<UserDataResponseModel>


    init {
        this.showProgress = repository.showProgress
        this.errorMessage = repository.errorMessage
        this.userResponseMutableLiveData = repository.userResponseMutableLiveData
    }

    fun callUserApi(token: String?, id: String?) {
        repository.loginApiCall(token, id)
    }


}