package com.servall.data.entities

import androidx.room.Entity

@Entity(tableName = "Appointment", primaryKeys = ["id", "datetime"])
data class AppointmentEntity(
    val id: String = "",
    val datetime: String = "",
    val customerId: String = "",
    val barberId: String = "",
    val toSynchronize: Boolean = true
)