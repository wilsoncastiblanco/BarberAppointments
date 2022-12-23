package com.servall.domain.entities

 data class Appointment(
    val id: String,
    val dateTime: Long,
    val customer: Customer,
    val barber: Barber
)
