package com.hackvita.pathcare.patient.home.ui

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackvita.pathcare.databinding.FragmentHomeBinding
import com.hackvita.pathcare.login.viewmodel.LoginActivityViewModel
import com.hackvita.pathcare.patient.home.model.HospitalRequestModel
import com.hackvita.pathcare.patient.home.model.ResHospital
import com.hackvita.pathcare.patient.home.viewmodel.HomeViewModel
import com.hackvita.pathcare.utility.PrefUtil
import com.hackvita.pathcare.videoCall.VideoCallActivity
import java.util.*

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    var token: String? = null
    private val REQUEST_FINE_LOCATION = 123
    lateinit var _city: String
    lateinit var searchCity: String
    lateinit var searchHospital: String
    lateinit var viewmodel: HomeViewModel
    lateinit var from: String
    var homeRecyclerViewAdapter: HomeAdapter? = null
    var homeSearchRecyclerViewAdapter: HomeAdapter? = null
    var hospitalData = ArrayList<ResHospital>()
    var searchHospitalData = ArrayList<ResHospital>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        viewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _city = getCity().toString()
        token = PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        observeHospitalsApiResponse()
        callNearbyHospitalsApi()
        intiListener()

        return binding.root
    }

    private fun intiListener() {

        binding.username=PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.USERNAME,"").toString()+","
        binding.searchBtn.setOnClickListener {
            from="search"
            callSearchHospitalsApi()
        }

//        binding.openVideoCall.setOnClickListener {
//            startActivity(Intent(requireContext(),VideoCallActivity::class.java))
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun getCity(): String? {
        // Check if the app has permission to access the device's location

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            // Get the location manager
            val locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

            // Get the last known location of the device
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            // Use Geocoder to get the city name from the latitude and longitude
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocation(location!!.latitude, location!!.longitude, 1)

            // Get the city name from the address
            return addresses?.get(0)?.locality
        } else {
            requestPermissions(
                requireActivity(),
                arrayOf(ACCESS_FINE_LOCATION),
                REQUEST_FINE_LOCATION
            )
        }
        return ""
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_FINE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callNearbyHospitalsApi()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Location permission not granted.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun callNearbyHospitalsApi() {
        from="location"
        var hospitalRequestModel = HospitalRequestModel(_city, null)
        viewmodel.hospitalApiCall(token.toString(), hospitalRequestModel)
    }

    private fun callSearchHospitalsApi() {
        if (binding.searchSpinner.getSelectedItem().toString() == "city") {
            searchCity=binding.searchEditText.text.toString()
            var hospitalRequestModel = HospitalRequestModel(searchCity, null)
            viewmodel.hospitalApiCall(token.toString(), hospitalRequestModel)
        } else if (binding.searchSpinner.getSelectedItem().toString() == "hospital") {
            searchHospital=binding.searchEditText.text.toString()
            var hospitalRequestModel = HospitalRequestModel(null, searchHospital)
            viewmodel.hospitalApiCall(token.toString(), hospitalRequestModel)
        }
    }

    private fun hideMyKeyboard() {
        val view = view
        if (view != null) {
            val hideMe =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken, 0)
        }
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

    }

    private fun observeHospitalsApiResponse() {
        viewmodel.hospitalResponse.observe(viewLifecycleOwner) {
            if (from=="search")
            {
                binding.searchHospitalRv.visibility=View.VISIBLE
                searchHospitalData = it.resHospital
                if(searchHospitalData.size==0)
                    Toast.makeText(requireContext(),"No hospitals found",Toast.LENGTH_LONG).show()
                binding.searchHospitalRv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
                homeSearchRecyclerViewAdapter = HomeAdapter(
                    requireContext(),
                    ArrayList(searchHospitalData)
                )
                binding.searchHospitalRv.adapter = homeSearchRecyclerViewAdapter
            }
            else {
                hospitalData = it.resHospital
                binding.hospitalRv.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                homeRecyclerViewAdapter = HomeAdapter(
                    requireContext(),
                    ArrayList(hospitalData)
                )
                binding.hospitalRv.adapter = homeRecyclerViewAdapter
            }
        }
    }
}