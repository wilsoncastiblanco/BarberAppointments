package com.servall.domain.repositories

import com.servall.domain.entities.Appointment
import com.servall.domain.entities.Response
import kotlinx.coroutines.flow.Flow

interface AppointmentPersistenceRepository {
    suspend fun createAppointment(
        appointment: Appointment,
        toSynchronize: Boolean
    ): Response<String>

    suspend fun createAppointment(
        dateTime: Long,
        barberId: String,
        customerId: String,
        toSynchronize: Boolean
    ): Response<String>

    suspend fun createAppointments(appointments: List<Appointment>, toSynchronize: Boolean): Response<List<Long>>
    fun listStreamCustomerAppointments(customerId: String): Flow<Response<List<Appointment>>>
    fun listStreamBarberAppointments(barberId: String): Flow<Response<List<Appointment>>>
}