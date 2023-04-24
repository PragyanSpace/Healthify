package com.hackvita.pathcare.register

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.hackvita.pathcare.R
import com.hackvita.pathcare.databinding.ActivityAskUserDetailAfterRegisterBinding
import com.hackvita.pathcare.login.ui.LoginActivity
import com.hackvita.pathcare.patient.PatientActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AskUserDetailAfterRegister : AppCompatActivity() {

    val GALLERY_REQUEST_CODE=1

    var id:String?=null

    lateinit var binding: ActivityAskUserDetailAfterRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_ask_user_detail_after_register)
        id=intent.getStringExtra("ID")
        checkForPermission()
        binding.dpImg.setOnClickListener {
            selectImage()
        }

        binding.moveToNextPage.setOnClickListener {
            startActivity(Intent(this@AskUserDetailAfterRegister, LoginActivity::class.java))
            finishAffinity()
        }



    }

    private fun checkForPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                GALLERY_REQUEST_CODE
            )
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            Glide.with(this)
                .load(imageUri)
                .into(binding.dpImg)

            GlobalScope.launch(Dispatchers.IO) {
                saveImageToFirebase(imageUri)
            }
        }
    }

    private fun saveImageToFirebase(uri:Uri?) {
        var storageRef = FirebaseStorage.getInstance().reference
        if (uri != null) {
            val hospital = storageRef.child("hospital/${id}.jpg")
            hospital.putFile(uri)
        }

    }
}