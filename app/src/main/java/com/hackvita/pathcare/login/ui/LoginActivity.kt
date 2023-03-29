package com.hackvita.pathcare.login.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hackvita.pathcare.R
import com.hackvita.pathcare.databinding.ActivitySigninBinding
import com.hackvita.pathcare.login.viewmodel.LoginActivityViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivitySigninBinding
    private var viewModel: LoginActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin)
    }
}