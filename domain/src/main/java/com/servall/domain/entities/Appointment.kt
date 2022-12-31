package com.servall.domain.entities


data class Appointment(
    val id: String,
    val datetime: Long,
    val customer: Customer,
    val barber: Barber
)
