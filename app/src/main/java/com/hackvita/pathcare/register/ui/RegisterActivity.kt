package com.hackvita.pathcare.register.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.hackvita.pathcare.MainActivity
import com.hackvita.pathcare.R
import com.hackvita.pathcare.databinding.ActivityRegisterBinding
import com.hackvita.pathcare.login.ui.LoginActivity
import com.hackvita.pathcare.register.model.RegisterRequestModel
import com.hackvita.pathcare.register.viewmodel.RegisterActivityViewModel
import com.hackvita.pathcare.utility.AppUrls
import com.hackvita.pathcare.utility.BaseUtil

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private var viewModel: RegisterActivityViewModel? = null
    var textWatchers: TextWatcher? = null
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProvider(this).get(RegisterActivityViewModel::class.java)
        sp = getSharedPreferences("login", MODE_PRIVATE);
        if (sp.getBoolean("logged", false)) {
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
        }
        observeShowProgress()
        observeErrorMessage()
        observeApiResponse()
        initListener()
    }

    private fun initListener() {
        initTextWatcher()
        binding.txtRegister.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        binding?.registerButton?.setOnClickListener {
            registerBtnClickFunction()
            hideMyKeyboard()
        }
        binding.name.addTextChangedListener(textWatchers)
        binding.password.addTextChangedListener(textWatchers)
        binding.phoneNo.addTextChangedListener(textWatchers)
        binding.email.addTextChangedListener(textWatchers)
    }

    private fun hideMyKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

    }

    private fun initTextWatcher() {
        textWatchers = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                if (editable == binding?.phoneNo?.editableText)
                    binding?.phoneNoLayout?.error = null
                if (editable == binding?.password?.editableText)
                    binding?.passwordLayout?.error = null
                if (editable == binding?.name?.editableText)
                    binding?.nameLayout?.error = null
                if (editable == binding?.email?.editableText)
                    binding?.emailLayout?.error = null

            }

        }
    }

    private fun registerBtnClickFunction() {
        val phone = binding.phoneNo.text?.trim().toString()
        val password = binding.password.text?.trim()?.toString()
        val name = binding.name.text?.trim()?.toString()
        val email = binding.email.text?.trim()?.toString()
        if (name.isNullOrBlank() || name.length > 255)
            binding?.nameLayout?.error = "Please Enter Name"
        else if (password.isNullOrBlank())
            binding?.passwordLayout?.error = "Please Enter Password"
        else if (password.length < 6 || password.length > 255) {
            binding.passwordLayout.error = "Please Enter Password"
            showSnackBar("Password length must be greater then 6 characters")
        } else if (phone.isNullOrBlank() || !BaseUtil.isValidPhoneNo(phone)) {
            binding.phoneNoLayout.error = "Please Enter Valid Phone Number"
        }
        else if (email.isNullOrBlank() || !BaseUtil.isValidEmail(email)) {
            binding.emailLayout.error = "Please Enter Valid Email"
        } else {
            val registerRequestModel =
                RegisterRequestModel(email,name, password)
            callRegisterApi(registerRequestModel)


        }
    }

    private fun showSnackBar(text: String) {
        val snack = Snackbar.make(
            binding.root,
            text,
            Snackbar.LENGTH_SHORT
        )
        snack.setBackgroundTint(resources.getColor(R.color.color_5658DD))
        snack.setTextColor(resources.getColor(R.color.white))
        snack.show()
    }

    private fun callRegisterApi(registerRequestModel: RegisterRequestModel) {
        val token=""
        viewModel?.callRegisterApi(token, registerRequestModel)
    }

    private fun observeApiResponse() {
        viewModel?.registerResponseMutableLiveData?.observe(this, Observer {
            Toast.makeText(
                applicationContext,
                "Registration Successful",
                // "Success:  Token-> ${it.data?.token}",
                Toast.LENGTH_SHORT
            ).show()
            AppUrls.TOKEN = "Bearer " + it.token
            if(it.success==true)
            {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun observeErrorMessage() {
    }

    private fun observeShowProgress() {

    }



}