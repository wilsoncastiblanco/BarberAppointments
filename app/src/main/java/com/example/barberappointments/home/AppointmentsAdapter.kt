package com.example.barberappointments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.barberappointments.R
import com.servall.domain.entities.Appointment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AppointmentsAdapter :
    ListAdapter<Appointment, AppointmentsAdapter.AppointmentViewHolder>(AppointmentsDiffUtil()) {

    class AppointmentViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(appointment: Appointment) {
            view.findViewById<TextView>(R.id.textViewBarberName).apply {
                text = appointment.barber.fullName
            }
            view.findViewById<TextView>(R.id.textViewDate).apply {
                val date = Date(appointment.datetime)
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                text = sdf.format(date)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = currentList[position]
        holder.bind(appointment)
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