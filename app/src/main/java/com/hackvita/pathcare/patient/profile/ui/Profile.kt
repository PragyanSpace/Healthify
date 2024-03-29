package com.hackvita.pathcare.patient.profile.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.rotationMatrix
import androidx.core.view.marginTop
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.hackvita.pathcare.databinding.FragmentProfileBinding
import com.hackvita.pathcare.login.ui.LoginActivity
import com.hackvita.pathcare.patient.profile.viewmodel.ProfileViewmodel
import com.hackvita.pathcare.utility.PrefUtil
import com.journeyapps.barcodescanner.BarcodeEncoder

class Profile : Fragment() {
    lateinit var binding:FragmentProfileBinding
    lateinit var viewmodel:ProfileViewmodel
    var shownDetails=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        viewmodel = ViewModelProvider(this).get(ProfileViewmodel::class.java)
        observeUserApiCall()
        observeShowProgress()
        callUserDetailApi()

        binding.card.setOnClickListener {
            if (shownDetails) {
                binding.details.visibility = View.GONE
                shownDetails = false
            } else {
                binding.details.visibility = View.VISIBLE
                shownDetails = true
            }
        }

        binding.logout.setOnClickListener {

            PrefUtil(requireContext()).removeSavedValue()
            startActivity(Intent(requireContext(),LoginActivity::class.java))
            activity?.finish()

        }


        return binding.root
    }

    private fun callUserDetailApi() {
        val token= PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        val id= PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.ID, "647384dfc6959dd3602f9764")

        viewmodel.callUserApi(token,id)
    }

    private fun observeUserApiCall() {
        viewmodel.userResponseMutableLiveData.observe(viewLifecycleOwner) {
            binding.name=it.user?.name.toString()
            binding.contact=it.user?.phoneNumber.toString()
            binding.email=it.user?.email
            binding.dob=it.user?.dob
            binding.blood=it.user?.bloodGroup
            binding.qr.setImageBitmap(generateQr(it.user?.id))
        }
    }

    private fun generateQr(id:String?):Bitmap? {
        val mWriter = MultiFormatWriter()
        try {
            val mMatrix = mWriter.encode(id, BarcodeFormat.QR_CODE, 400, 400);
            val mEncoder = BarcodeEncoder();
            return mEncoder.createBitmap(mMatrix)
        } catch (e: WriterException) {
            e.printStackTrace();
        }
        return null
    }

    private fun observeShowProgress() {
        viewmodel?.showProgress?.observe(viewLifecycleOwner){

            if(it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.mainView.visibility=View.GONE
            }
            else {
                binding.progressBar.visibility = View.GONE
                binding.mainView.visibility=View.VISIBLE
            }

        }

    }
}