package com.hackvita.pathcare.appointment_booking_detail_bottomsheet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hackvita.pathcare.databinding.MonthsRvItemBinding

class MonthsItemRvAdapter(
    var context: Context,
    var dates: ArrayList<String>?
):RecyclerView.Adapter<MonthsItemRvAdapter.ViewHolder>() {

    class ViewHolder(val binding: MonthsRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(data:String?,context: Context, position: Int) {

            binding.day.text=data

        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MonthsRvItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MonthsItemRvAdapter.ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dates?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dates?.get(position).let {
            holder.bindView(it, context, position)
        }
    }
}