package com.example.barberappointments.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.barberappointments.R
import com.servall.domain.entities.Appointment

class AppointmentsAdapter(context: Context) : RecyclerView.Adapter<AppointmentViewHolder>() {

    private val appointments = mutableListOf<Appointment>()
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = layoutInflater.inflate(R.layout.element_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.nameTextView.text = appointment.barber.fullName
    }

    fun setAppointments(appointments: List<Appointment>) {
        this.appointments.addAll(appointments)
        notifyDataSetChanged()
    }

}

class AppointmentViewHolder : RecyclerView.ViewHolder {

    val nameTextView: TextView

    constructor(itemView: View) : super(itemView) {
        nameTextView = itemView.findViewById(R.id.textViewName)
    }
}