package com.hackvita.pathcare.patient.appointments.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.hackvita.pathcare.VideoCallActivity
import com.hackvita.pathcare.databinding.ApprovedAppointmentsLayoutBinding
import com.hackvita.pathcare.patient.appointments.model.Appointments
import java.io.Serializable

class MyAppointmentsAdapter (
        var context: Context,
        var appointments: ArrayList<Appointments>?
    ) : RecyclerView.Adapter<MyAppointmentsAdapter.ViewHolder>(), Serializable {

        class ViewHolder(val binding: ApprovedAppointmentsLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bindView(appointment: Appointments?, context: Context, position: Int) {

                binding.hosName = appointment?.hospitalId?.name
                binding.docName = appointment?.doctorId?.name
                binding.date=appointment?.appointmentDate

                binding.root.setOnClickListener {

                    var intent= Intent(context, VideoCallActivity::class.java)
                    startActivity(context,intent,null)


                }

            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ApprovedAppointmentsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            appointments?.get(position).let {
                holder.bindView(it, context, position)
            }
        }

        override fun getItemCount(): Int {
            return appointments?.size ?: 0;
        }


}