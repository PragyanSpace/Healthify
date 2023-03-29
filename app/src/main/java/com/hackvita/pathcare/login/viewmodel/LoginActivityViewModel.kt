package com.hackvita.pathcare.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hackvita.pathcare.login.model.LoginRequestModel
import com.hackvita.pathcare.login.model.LoginResponseModel
import com.hackvita.pathcare.login.repository.LoginRepository

class LoginActivityViewModel(): ViewModel() {

    private val repository = LoginRepository()
    val showProgress: LiveData<Boolean>
    val errorMessage: LiveData<String>
    val loginResponseMutableLiveData: LiveData<LoginResponseModel>


    init {
        this.showProgress = repository.showProgress
        this.errorMessage = repository.errorMessage
        this.loginResponseMutableLiveData = repository.loginResponseMutableLiveData
    }

    fun callLoginApi(token: String?, loginRequestModel: LoginRequestModel) {
        repository.loginApiCall(token, loginRequestModel)
    }


}