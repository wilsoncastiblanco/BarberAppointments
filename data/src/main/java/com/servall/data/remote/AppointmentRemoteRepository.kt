package com.servall.data.remote

import com.servall.data.entities.AppointmentNetworkDto
import com.servall.data.extensions.handleApiResponse
import com.servall.data.toModel
import com.servall.domain.entities.Appointment
import com.servall.domain.entities.Response
import com.servall.domain.repositories.AppointmentRepository
import kotlinx.coroutines.flow.Flow

//Single Source of truth
//Offline first
class AppointmentRemoteRepository(
    private val api: ApiCalls,
    private val safeApiCall: SafeApiCall
) : AppointmentRepository {
    override suspend fun createAppointment(
        dateTime: Long,
        barberId: String,
        customerId: String
    ): Response<String> {
        return safeApiCall {
            val response = api.createAppointment(
                AppointmentNetworkDto(
                    datetime = dateTime,
                    barberId = barberId,
                    clientId = customerId
                )
            )
            response.handleApiResponse { body -> body.id }
        }
    }

    override suspend fun createAppointment(
        id: String,
        dateTime: Long,
        barberId: String,
        customerId: String
    ): Response<String> {
        TODO("Not yet implemented")
    }

    override fun listStreamCustomerAppointments(customerId: String): Flow<Response<List<Appointment>>> {
        TODO("Not yet implemented")
    }

    override fun listStreamBarberAppointments(barberId: String): Flow<Response<List<Appointment>>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAppointments(customerId: String): Response<List<Appointment>> {
        return safeApiCall {
            api.listAppointments(customerId).handleApiResponse { it }
        }
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