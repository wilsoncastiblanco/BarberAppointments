package com.example.barberappointments.availablebarbers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.barberappointments.R
import com.servall.domain.entities.Barber

class AvailableBarbersAdapter(
    private val onBarberSelected: (Barber) -> Unit
) : RecyclerView.Adapter<BarbersViewHolder>() {

    private val barbers = mutableListOf<Barber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarbersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_barber, parent, false)
        return BarbersViewHolder(view, onBarberSelected)
    }

    override fun getItemCount(): Int {
        return barbers.size
    }

    override fun onBindViewHolder(holder: BarbersViewHolder, position: Int) {
        val barber = barbers[position]
        holder.bind(barber)
    }

    fun setBarbers(barbers: List<Barber>) {
        this.barbers.addAll(barbers)
        notifyDataSetChanged()
    }

}

class BarbersViewHolder(itemView: View, val onBarberSelected: (Barber) -> Unit) : RecyclerView.ViewHolder(itemView) {

    private val nameTextView: TextView

    init {
        nameTextView = itemView.findViewById(R.id.textViewName)
    }

    fun bind(barber: Barber) {
        itemView.setOnClickListener { onBarberSelected(barber) }
        nameTextView.text = barber.fullName
    }
}