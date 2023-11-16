package com.hackvita.pathcare.appointment_booking_detail_bottomsheet.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hackvita.pathcare.R
import com.hackvita.pathcare.databinding.AppointmentDetailEntryBottomsheetBinding
import com.hackvita.pathcare.hospitalDetail.model.AppointmentRequestModel
import com.hackvita.pathcare.hospitalDetail.viewmodel.HospitalDetailViewmodel
import com.hackvita.pathcare.utility.PrefUtil
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

class AppointmentDetailBottomsheet:BottomSheetDialogFragment() {
    lateinit var binding:AppointmentDetailEntryBottomsheetBinding
    var currentYear=0
    lateinit var hosId: String
    lateinit var date: String

    private var viewModel: HospitalDetailViewmodel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<AppointmentDetailEntryBottomsheetBinding>(inflater, R.layout.appointment_detail_entry_bottomsheet,container,false)
        viewModel = ViewModelProvider(this).get(HospitalDetailViewmodel::class.java)

        observeAppointmentApiResponse()
        observeShowProgress()

        binding.datePicker.setOnClickListener {
            showDatePickerDialog()
        }

        hosId=activity?.intent?.getStringExtra("HOSPITAL_ID").toString()
        date="2023-07-01"

        binding.bookBtn.setOnClickListener {
            callAppointmentApi()
        }

//        setSpinner()
//        binding.monthYearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                val selectedItem = parent.getItemAtPosition(position).toString()
//                var months=getDatesInMonth(currentYear,position+1)
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Do nothing
//            }
//        }

        return binding.root
    }

//    private fun setSpinner() {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR).toString()
//        val months = arrayOf(
//            "Jan,$year", "Feb,$year", "Mar,$year", "Apr,$year", "May,$year", "Jun,$year",
//            "July,$year", "Aug,$year", "Sept,$year", "Oct,$year", "Nov,$year", "Dec,$year"
//        )
//        currentYear=year.toInt()
//        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, months)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.monthYearSpinner.adapter = adapter
//    }

    private fun getDatesInMonth(year: Int, month: Int): ArrayList<LocalDate> {
        val dates = ArrayList<LocalDate>()
        val numOfDays = YearMonth.of(year, month).lengthOfMonth()
        val firstDay = LocalDate.of(year, month, 1)
        for (i in 0 until numOfDays) {
            val date = firstDay.plusDays(i.toLong())
            dates.add(date)
        }
        return dates
    }

    private fun callAppointmentApi() {
        val token =
            PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        val id =
            PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.ID, "647384dfc6959dd3602f9764")
        date="2023-07-01"
        val description=binding.descriptionBox.text.toString()
        var appointmentRequestModel= AppointmentRequestModel(hosId,id.toString(),date,description)
        viewModel?.callRequestAppointment(token,appointmentRequestModel)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            // Handle the selected date
            var selectedDate=""
            if(selectedDay<10)
            {selectedDate = "0$selectedDay/${selectedMonth + 1}/$selectedYear"}
            if(selectedMonth<10)
            {selectedDate = "$selectedDay/0${selectedMonth + 1}/$selectedYear"}
            binding.datePicker.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }


    private fun observeAppointmentApiResponse() {
        viewModel?.appointmentResponseMutableLiveData?.observe(viewLifecycleOwner) {
            if (it.success == true) {
                binding.bookBtn.visibility = View.GONE
                Toast.makeText(requireContext(),it.message.toString(),Toast.LENGTH_LONG).show()
                dismiss()
            }
        }
    }

    private fun observeShowProgress() {
        viewModel?.showProgress?.observe(this){

            if(it)
                binding.progressBar.visibility=View.VISIBLE
            else
                binding.progressBar.visibility=View.GONE

        }

    }
}