package com.servall.domain.repositories

import com.servall.domain.entities.Appointment
import com.servall.domain.entities.Response

interface AppointmentRepository {
    suspend fun createAppointment(
        dateTime: Long,
        barberId: String,
        customerId: String
    ): Response<Appointment>

    suspend fun listCustomerAppointments(customerId: String): Response<List<Appointment>>
    suspend fun listBarberAppointments(barberId: String): Response<List<Appointment>>
    suspend fun getAppointmentById(appointmentId: String): Response<Appointment>
    suspend fun configureAppointment(barberId: String, isAccepted: Boolean): Response<Appointment>
}