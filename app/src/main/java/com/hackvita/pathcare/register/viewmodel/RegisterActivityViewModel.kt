package com.hackvita.pathcare.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hackvita.pathcare.register.model.RegisterRequestModel
import com.hackvita.pathcare.register.model.RegisterResponseModel
import com.hackvita.pathcare.register.repository.RegisterRepository

class RegisterActivityViewModel(): ViewModel(){

    private val repository= RegisterRepository()
     val showProgress:LiveData<Boolean>
    val errorMessage: LiveData<String>
    val registerResponseMutableLiveData: LiveData<RegisterResponseModel>


    init {
        this.showProgress = repository.showProgress
        this.errorMessage = repository.errorMessage
        this.registerResponseMutableLiveData = repository.registerResponseMutableLiveData
    }

    fun callRegisterApi(token: String?, registerRequestModel: RegisterRequestModel) {
        repository.registerApiCall(token, registerRequestModel)
    }
}