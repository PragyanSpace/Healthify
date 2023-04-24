package com.hackvita.pathcare.appointment_booking_detail_bottomsheet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hackvita.pathcare.R
import com.hackvita.pathcare.databinding.AppointmentDetailEntryBottomsheetBinding
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

class AppointmentDetailBottomsheet:BottomSheetDialogFragment() {
    lateinit var binding:AppointmentDetailEntryBottomsheetBinding
    var currentYear=0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<AppointmentDetailEntryBottomsheetBinding>(inflater, R.layout.appointment_detail_entry_bottomsheet,container,false)

        setSpinner()
        binding.monthYearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                var months=getDatesInMonth(currentYear,position+1)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        return binding.root
    }

    private fun setSpinner() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR).toString()
        val months = arrayOf(
            "Jan,$year", "Feb,$year", "Mar,$year", "Apr,$year", "May,$year", "Jun,$year",
            "July,$year", "Aug,$year", "Sept,$year", "Oct,$year", "Nov,$year", "Dec,$year"
        )
        currentYear=year.toInt()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, months)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.monthYearSpinner.adapter = adapter
    }

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
}