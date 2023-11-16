package com.hackvita.pathcare.patient.home.ui

import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackvita.pathcare.databinding.FragmentHomeBinding
import com.hackvita.pathcare.patient.home.model.Hospitals
import com.hackvita.pathcare.patient.home.model.NearbyHospitalRequestModel
import com.hackvita.pathcare.patient.home.viewmodel.HomeViewModel
import com.hackvita.pathcare.utility.PrefUtil
import java.util.*

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    var token: String? = null
    private val REQUEST_FINE_LOCATION = 123
    lateinit var _city: String
    lateinit var _latitude: String
    lateinit var _longitude: String
    lateinit var searchCity: String
    lateinit var searchHospital: String
    lateinit var viewmodel: HomeViewModel
    var homeRecyclerViewAdapter: HomeAdapter? = null
    var homeSearchRecyclerViewAdapter: HomeSearchAdapter? = null
    var nearbyHospitalData = arrayListOf<Hospitals>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        viewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _city = getCity().toString()
        observeNearbyHospitalsApiResponse()
        getLatitudeLongitude()
        token = PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.TOKEN, "")
//        observeHospitalsApiResponse()
        observeShowProgress()
        intiListener()

        return binding.root
    }

    private fun intiListener() {

        binding.username ="hi ${PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.USERNAME, "Pragyan")}"
                .toString() + ","
        binding.searchBtn.setOnClickListener {
            var intent=Intent(requireContext(),SearchHospitalActivity::class.java)
            intent.putExtra("mode",binding.searchSpinner.selectedItem.toString())
            intent.putExtra("search",binding.searchEditText.text.toString())
            startActivity(intent)
            hideMyKeyboard()
        }


        binding.refreshLayout.setOnRefreshListener {
            callNearbyHospitalsApi()
            binding.refreshLayout.isRefreshing = false
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

    fun getLatitudeLongitude() {
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

            _latitude = location?.latitude.toString()
            _longitude = location?.longitude.toString()
            callNearbyHospitalsApi()
        } else {
            requestPermissions(
                requireActivity(),
                arrayOf(ACCESS_FINE_LOCATION),
                REQUEST_FINE_LOCATION
            )
        }
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
        val token = PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        var hospitalRequestModel = NearbyHospitalRequestModel(_latitude, _longitude)
        viewmodel.nearbyHospitalApiCall(token.toString(), hospitalRequestModel)
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

    private fun observeNearbyHospitalsApiResponse() {
        viewmodel.nearbyHospitalResponse.observe(viewLifecycleOwner) {
            nearbyHospitalData = it.hospitals
            binding.hospitalRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
            homeRecyclerViewAdapter = HomeAdapter(
                requireContext(),
                ArrayList(nearbyHospitalData)
            )
            binding.hospitalRv.adapter = homeRecyclerViewAdapter
            if (it.hospitals.isEmpty())
            {
                binding.hospitalRv.visibility=View.GONE
                binding.nearbyHospitalsTV.visibility=View.VISIBLE
            }
            else {
                binding.hospitalRv.visibility = View.VISIBLE
                binding.nearbyHospitalsTV.visibility = View.GONE
            }
        }
    }

    private fun observeShowProgress() {
        viewmodel?.showProgress?.observe(viewLifecycleOwner){

            if(it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.hospitalRv.visibility = View.GONE
            }
            else {
                binding.progressBar.visibility = View.GONE
                binding.hospitalRv.visibility = View.VISIBLE
            }

        }

    }
}