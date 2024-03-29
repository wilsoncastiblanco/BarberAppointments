package com.servall.domain.repositories

import com.servall.domain.entities.Appointment
import com.servall.domain.entities.Response
import kotlinx.coroutines.flow.Flow

interface AppointmentRepository {
    suspend fun createAppointment(
        dateTime: Long,
        barberId: String,
        customerId: String,
    ): Response<String>

    suspend fun createAppointment(
        id: String,
        dateTime: Long,
        barberId: String,
        customerId: String
    ): Response<String>

    fun listStreamCustomerAppointments(customerId: String): Flow<Response<List<Appointment>>>
    fun listStreamBarberAppointments(barberId: String): Flow<Response<List<Appointment>>>

    suspend fun fetchAppointments(customerId: String): Response<List<Appointment>>
    suspend fun listBarberAppointments(barberId: String): Response<List<Appointment>>
    suspend fun getAppointmentById(appointmentId: String): Response<Appointment>
    suspend fun configureAppointment(barberId: String, isAccepted: Boolean): Response<Appointment>
}