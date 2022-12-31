package com.servall.data.datasource

import com.servall.data.database.AppointmentDatabaseRepository
import com.servall.data.database.UserDatabaseRepository
import com.servall.data.exceptions.OfflineException
import com.servall.data.remote.AppointmentRemoteRepository
import com.servall.domain.entities.Appointment
import com.servall.domain.entities.Response
import com.servall.domain.entities.User
import com.servall.domain.repositories.AppointmentRepository
import kotlinx.coroutines.flow.Flow

class AppointmentsDataSourceRepository(
    private val appointmentRemoteRepository: AppointmentRemoteRepository,
    private val appointmentsDatabaseRepository: AppointmentDatabaseRepository,
    private val userDatabaseRepository: UserDatabaseRepository
) : AppointmentRepository {

    override suspend fun createAppointment(
        dateTime: Long,
        barberId: String,
        customerId: String
    ): Response<String> {
        val remoteResult = appointmentRemoteRepository.createAppointment(
            dateTime = dateTime,
            barberId = barberId,
            customerId = customerId
        )
        return when (remoteResult) {
            is Response.Error -> {
                if (remoteResult.exception is OfflineException) {
                    appointmentsDatabaseRepository.createAppointment(
                        dateTime = dateTime,
                        barberId = barberId,
                        customerId = customerId
                    )
                } else {
                    remoteResult
                }
            }

            is Response.Success -> {
                appointmentsDatabaseRepository.createAppointment(
                    id = remoteResult.data,
                    dateTime = dateTime,
                    barberId = barberId,
                    customerId = customerId
                )
            }
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
        return appointmentsDatabaseRepository.listStreamCustomerAppointments(customerId)
    }

    override suspend fun fetchAppointments(customerId: String): Response<List<Appointment>> {
        when (val result = appointmentRemoteRepository.fetchAppointments(customerId)) {
            is Response.Error -> appointmentsDatabaseRepository.fetchAppointments(customerId)
            is Response.Success -> {
                result.data.forEach { appointment ->
                    userDatabaseRepository.createUser(
                        User(
                            id = appointment.barber.id,
                            fullName = appointment.barber.fullName,
                            userName = appointment.barber.fullName,
                            role = "BARBER",
                            password = null
                        )
                    )
                    userDatabaseRepository.createUser(
                        User(
                            id = appointment.customer.id,
                            fullName = appointment.customer.fullName,
                            userName = appointment.customer.fullName,
                            role = "CUSTOMER",
                            password = null
                        )
                    )
                    appointmentsDatabaseRepository.createAppointment(
                        dateTime = appointment.datetime,
                        barberId = appointment.barber.id,
                        customerId = appointment.customer.id
                    )
                }
            }
        }
        return appointmentsDatabaseRepository.fetchAppointments(customerId)
    }

    override fun listStreamBarberAppointments(barberId: String): Flow<Response<List<Appointment>>> {
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