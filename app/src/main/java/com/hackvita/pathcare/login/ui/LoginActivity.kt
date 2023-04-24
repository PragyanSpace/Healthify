package com.hackvita.pathcare.login.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.hackvita.pathcare.MainActivity
import com.hackvita.pathcare.R
import com.hackvita.pathcare.databinding.ActivitySigninBinding
import com.hackvita.pathcare.login.model.LoginRequestModel
import com.hackvita.pathcare.login.viewmodel.LoginActivityViewModel
import com.hackvita.pathcare.patient.PatientActivity
import com.hackvita.pathcare.register.ui.RegisterActivity
import com.hackvita.pathcare.utility.AppUrls
import com.hackvita.pathcare.utility.BaseUtil
import com.hackvita.pathcare.utility.PrefUtil

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivitySigninBinding
    private var viewModel: LoginActivityViewModel? = null
    var textWatchers: TextWatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin)
        viewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)

        if (PrefUtil(this).sharedPreferences?.getBoolean(PrefUtil.IS_LOGIN, false) == true) {
            val intent = Intent(this@LoginActivity, PatientActivity::class.java)
            startActivity(intent)
            finish()
        }
        observeShowProgress()
        observeErrorMessage()
        observeApiResponse()
        initListener()

    }


    private fun initListener() {
        binding?.txtRegister?.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        initTextWatcher()
        binding?.loginBtn?.setOnClickListener {
            hideMyKeyboard()
            loginBtnClickFunction()
        }
        binding?.email?.addTextChangedListener(textWatchers)
        binding?.password?.addTextChangedListener(textWatchers)
    }


    private fun initTextWatcher() {
        textWatchers = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                if (editable == binding?.email?.editableText)
                    binding?.emailLayout?.error = null
                if (editable == binding?.password?.editableText)
                    binding?.passwordLayout?.error = null
            }

        }
    }

    private fun loginBtnClickFunction() {
        val email = binding?.email?.text?.toString()?.trim()
        val password = binding?.password?.text?.toString()?.trim()
        if (email.isNullOrBlank() || !BaseUtil.isValidEmail(email))
            binding?.emailLayout?.error = "Please Enter Valid Email"
        else if (password.isNullOrBlank())
            binding?.passwordLayout?.error = "Please Enter Password"
        else if (password.length < 6 || password.length > 255)
            binding?.passwordLayout?.error = "Password length must be greater then 6 characters"
        else
        {
            val loginRequestModel = LoginRequestModel(email, password)
            callLoginApi(loginRequestModel)
        }
    }

    private fun callLoginApi(loginRequestModel: LoginRequestModel) {
        val token =
            PrefUtil(this@LoginActivity).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        viewModel?.callLoginApi(token, loginRequestModel)
    }

    private fun observeApiResponse() {
        viewModel?.loginResponseMutableLiveData?.observe(this, Observer {
            AppUrls.TOKEN = "Bearer " + it.token.toString()
            Log.i(TAG, "observeApiResponse: ${it.user}")
            PrefUtil(this@LoginActivity).sharedPreferences?.edit()
                ?.putBoolean(PrefUtil.IS_LOGIN, true)?.apply()
            PrefUtil(this@LoginActivity).sharedPreferences?.edit()
                ?.putString(PrefUtil.TOKEN, "Bearer " + it.token)?.apply()
            PrefUtil(this@LoginActivity).sharedPreferences?.edit()
                ?.putString(PrefUtil.ID, it.user?.Id)?.apply()
            PrefUtil(this@LoginActivity).sharedPreferences?.edit()
                ?.putString(PrefUtil.USERNAME, it.user?.name)?.apply()
            if(it.success==true)
            {
                val intent = Intent(this@LoginActivity, PatientActivity::class.java)
                intent.putExtra("username",it.user?.name)
                startActivity(intent)
                finish()
            }
        })
    }
    

    private fun observeErrorMessage() {
        viewModel?.errorMessage?.observe(this, Observer {
                val snack = Snackbar.make(binding.root, "${it}", Snackbar.LENGTH_SHORT)
                snack.setBackgroundTint(resources.getColor(R.color.color_5658DD))
                snack.setTextColor(resources.getColor(R.color.white))
                snack.show()
            })
    }

    private fun observeShowProgress() {
        viewModel?.showProgress?.observe(this, Observer {
            buttonEnableAndDisable(it)
        })

    }

    private fun buttonEnableAndDisable(value: Boolean) {
        if (value) {
            binding.loginBtn.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.loginBtn.isEnabled = true
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun hideMyKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

    }
}