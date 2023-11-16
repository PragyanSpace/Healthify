package com.hackvita.pathcare.hospitalDetail.ui


import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hackvita.pathcare.R
import com.hackvita.pathcare.appointment_booking_detail_bottomsheet.ui.AppointmentDetailBottomsheet
import com.hackvita.pathcare.databinding.ActivityHospitalDetailBinding
import com.hackvita.pathcare.hospitalDetail.model.AppointmentRequestModel
import com.hackvita.pathcare.hospitalDetail.viewmodel.HospitalDetailViewmodel
import com.hackvita.pathcare.utility.PrefUtil
import java.util.*

class HospitalDetail : AppCompatActivity() {

    lateinit var binding: ActivityHospitalDetailBinding
    private var viewModel: HospitalDetailViewmodel? = null
    lateinit var hosId: String
    lateinit var date: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_hospital_detail)
        viewModel = ViewModelProvider(this).get(HospitalDetailViewmodel::class.java)
        hosId=intent.getStringExtra("HOSPITAL_ID").toString()
        observeApiResponse()
        observeShowProgress()
        observeAppointmentApiResponse()
        callHospitalDetailApi()
        initListener()

    }

    private fun initListener() {
        binding.bookBtn.setOnClickListener {
                val bottomSheetDialog = AppointmentDetailBottomsheet()
                bottomSheetDialog.show(supportFragmentManager, "CustomBottomSheetDialogFragment")
        }
//        binding.bookBtn.setOnClickListener {
//            // Get the current date
//            val c = Calendar.getInstance()
//            val year = c.get(Calendar.YEAR)
//            val month = c.get(Calendar.MONTH)
//            val day = c.get(Calendar.DAY_OF_MONTH)
//
//            // Create a DatePickerDialog
//            val datePickerDialog = DatePickerDialog(
//                this,
//                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//                    // Set the dateEditText text to the selected date
//                    date="$dayOfMonth-${monthOfYear + 1}-$year"
//                    Log.d("date",date)
//                    callAppointmentApi()
//                },
//                year,
//                month,
//                day
//            )
//
//            // Show the DatePickerDialog
//            datePickerDialog.show()
//        }
    }

    private fun callHospitalDetailApi() {
        val token =
            PrefUtil(this@HospitalDetail).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        viewModel?.callHospitalDetail(token,hosId)
    }


    private fun observeApiResponse() {
        viewModel?.hospitalResponseMutableLiveData?.observe(this, Observer {
            if(it.success==true)
            {
                var hosContact=""
                for (name in it.hospital?.contactNumber!!) {
                    hosContact+="$name , "
                }
                var hosDept=""
                for (name in it.hospital?.departments!!) {
                    hosDept+="${name.deptName} , "
                }
                binding.name= it.hospital?.name.toString()
                binding.email=it.hospital?.email.toString()
                binding.contact=hosContact.trim()
                binding.departments=hosDept.trim()
                binding.address=it.hospital?.address?.city.toString()

                if(it.hospital!!.name=="JMCH")
                {
                    Glide.with(this).load(R.drawable.jmch_logo).into(binding.hospitalImg)
                }
                else if(it.hospital!!.name=="GMCH")
                {
                    Glide.with(this).load(R.drawable.gmch_logo).into(binding.hospitalImg)
                }
            }
        })
    }

    private fun callAppointmentApi() {
        val token =
            PrefUtil(this@HospitalDetail).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        val id =
            PrefUtil(this@HospitalDetail).sharedPreferences?.getString(PrefUtil.ID, "")
        var appointmentRequestModel=AppointmentRequestModel(hosId,id,date)
        viewModel?.callRequestAppointment(token,appointmentRequestModel)
    }


    private fun observeAppointmentApiResponse() {
        viewModel?.appointmentResponseMutableLiveData?.observe(this, Observer {
            if(it.success==true)
            {
                binding.bookBtn.visibility= View.GONE
                binding.message=it.message.toString()
                binding.appointmentTV.visibility=View.VISIBLE
            }
        })
    }

    private fun observeShowProgress() {
        viewModel?.showProgress?.observe(this){

            if(it) {
                binding.progressBar.visibility=View.VISIBLE
                binding.linearLayout.visibility=View.GONE
                binding.bookBtn.visibility=View.GONE
            }
            else
                binding.progressBar.visibility=View.GONE
            binding.linearLayout.visibility=View.VISIBLE
            binding.bookBtn.visibility=View.VISIBLE

        }

    }


}