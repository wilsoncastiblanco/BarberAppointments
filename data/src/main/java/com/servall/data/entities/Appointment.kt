package com.servall.data.entities

import com.servall.domain.entities.Barber
import com.servall.domain.entities.Customer
import com.servall.domain.entities.User

data class AppointmentResponse(
    val id: String,
    val datetime: Long,
    val barber: Barber,
    val customer: Customer
)

data class AppointmentDto(
    val id: String = "-1",
    val datetime: Long,
    val barberId: String,
    val clientId: String
)
