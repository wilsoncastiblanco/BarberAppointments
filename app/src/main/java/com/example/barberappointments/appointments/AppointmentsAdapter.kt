package com.example.barberappointments.appointments

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.servall.domain.entities.Appointment

class AppointmentsAdapter :
    ListAdapter<Appointment, AppointmentsAdapter.AppointmentViewHolder>(AppointmentsDiffUtil()) {

    class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        return AppointmentViewHolder(parent.rootView)//TODO: Finish the adapter
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {

    }
}

class AppointmentsDiffUtil :
    DiffUtil.ItemCallback<Appointment>() {
    override fun areItemsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
        return oldItem == newItem
    }

}