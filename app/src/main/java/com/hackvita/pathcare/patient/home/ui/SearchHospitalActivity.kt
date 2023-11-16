package com.hackvita.pathcare.patient.home.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackvita.pathcare.R
import com.hackvita.pathcare.databinding.ActivitySearchHospitalBinding
import com.hackvita.pathcare.patient.home.model.HospitalRequestModel
import com.hackvita.pathcare.patient.home.model.SearchedHospitals
import com.hackvita.pathcare.patient.home.viewmodel.HomeViewModel
import com.hackvita.pathcare.utility.PrefUtil
import java.util.ArrayList

class SearchHospitalActivity : AppCompatActivity() {

    lateinit var viewmodel: HomeViewModel

    var homeSearchRecyclerViewAdapter: HomeSearchAdapter? = null

    var searchHospitalData = ArrayList<SearchedHospitals>()

    var mode=""
    var search=""


    lateinit var binding:ActivitySearchHospitalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_search_hospital)

        viewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)

        observeHospitalsApiResponse()
        observeShowProgress()


        mode= intent.getStringExtra("mode").toString()
        search=intent.getStringExtra("search").toString()

        callSearchHospitalsApi()



    }


    private fun observeHospitalsApiResponse() {
        viewmodel.hospitalResponse.observe(this) {
                binding.hospitalRv.visibility = View.VISIBLE
                searchHospitalData = it.hospitals
                if (searchHospitalData.size == 0)
                    Toast.makeText(this@SearchHospitalActivity, "No hospitals found", Toast.LENGTH_LONG).show()
                binding.hospitalRv.layoutManager =
                    LinearLayoutManager(this@SearchHospitalActivity, LinearLayoutManager.HORIZONTAL, false);
                homeSearchRecyclerViewAdapter = HomeSearchAdapter(
                    this@SearchHospitalActivity,
                    ArrayList(searchHospitalData)
                )
                binding.hospitalRv.adapter = homeSearchRecyclerViewAdapter
            if(it.success==false)
            {
                Toast.makeText(this@SearchHospitalActivity,it.message,Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun callSearchHospitalsApi() {
        val token = PrefUtil(this@SearchHospitalActivity).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        if (mode == "city") {
            var hospitalRequestModel = HospitalRequestModel(search, null)
            viewmodel.hospitalApiCall(token.toString(), hospitalRequestModel)
        } else if (mode == "hospital") {
            var hospitalRequestModel = HospitalRequestModel(null,search)
            viewmodel.hospitalApiCall(token.toString(), hospitalRequestModel)
        }
    }


    private fun observeShowProgress() {
        viewmodel?.showProgress?.observe(this){

            if(it)
            {
                binding.progressBar.visibility=View.VISIBLE
                binding.hospitalRv.visibility=View.GONE
            }
            else {
                binding.progressBar.visibility = View.GONE
                binding.hospitalRv.visibility=View.VISIBLE
            }

        }

    }


}