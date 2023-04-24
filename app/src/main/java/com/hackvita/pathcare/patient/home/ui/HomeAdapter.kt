package com.hackvita.pathcare.patient.home.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.hackvita.pathcare.R
import com.hackvita.pathcare.databinding.HospitalsItemBinding
import com.hackvita.pathcare.hospitalDetail.ui.HospitalDetail
import com.hackvita.pathcare.patient.home.model.ResHospital

class HomeAdapter(
    var context: Context,
    var hospitals: ArrayList<ResHospital>?
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(val binding: HospitalsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(hospitalData: ResHospital?, context: Context, position: Int) {

            binding.name.text = hospitalData?.name.toString()
            binding.address.text = hospitalData?.address?.city.toString()

            binding.root.setOnClickListener {
                var intent = Intent(context, HospitalDetail::class.java)
                intent.putExtra("HOSPITAL_ID", hospitalData?.Id)
                startActivity(context, intent, null)
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HospitalsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        hospitals?.get(position).let {
            holder.bindView(it, context, position)
        }
    }

    private fun handleClickOfView(
        holder: ViewHolder,
        position: Int
    ) {

    }

    override fun getItemCount(): Int {
        return hospitals?.size ?: 0;
    }


}