package com.servall.data.remote

import com.servall.domain.entities.Appointment
import com.servall.domain.entities.Response
import com.servall.domain.repositories.AppointmentRepository

class AppointmentRemoteRepository: AppointmentRepository {
    override suspend fun createAppointment(
        dateTime: Long,
        barberId: String,
        customerId: String
    ): Response<Appointment> {
        TODO("Not yet implemented")
    }

    override suspend fun listCustomerAppointments(customerId: String): Response<List<Appointment>> {
        TODO("Not yet implemented")
    }

    override suspend fun listBarberAppointments(barberId: String): Response<List<Appointment>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAppointmentById(appointmentId: String): Response<Appointment> {
        TODO("Not yet implemented")
    }

    override suspend fun configureAppointment(
        barberId: String,
        isAccepted: Boolean
    ): Response<Appointment> {
        TODO("Not yet implemented")
    }
}