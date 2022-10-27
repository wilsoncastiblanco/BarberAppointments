package com.servall.domain.entities

data class Availability(
    val weekDayRange: String,
    val hourRange: String,
    val available: Boolean,
    val barber: User
)