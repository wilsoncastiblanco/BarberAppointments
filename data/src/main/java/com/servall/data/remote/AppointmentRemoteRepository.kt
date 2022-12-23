package com.servall.data.remote

import com.servall.data.entities.AppointmentDto
import com.servall.data.extensions.handleApiResponse
import com.servall.data.toModel
import com.servall.domain.entities.Appointment
import com.servall.domain.entities.Response
import com.servall.domain.repositories.AppointmentRepository

class AppointmentRemoteRepository(
    private val api: ApiCalls,
    private val safeApiCall: SafeApiCall
) : AppointmentRepository {
    override suspend fun createAppointment(
        dateTime: Long,
        barberId: String,
        customerId: String
    ): Response<Appointment> {
        return safeApiCall {
            val response = api.createAppointment(
                AppointmentDto(
                    datetime = dateTime,
                    barberId = barberId,
                    clientId = customerId
                )
            )
            response.handleApiResponse { body -> body.toModel() }
        }
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