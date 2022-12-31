package com.servall.data.database

import com.servall.data.entities.AppointmentEntity
import com.servall.data.mappers.toModel
import com.servall.domain.entities.Appointment
import com.servall.domain.entities.Response
import com.servall.domain.repositories.AppointmentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AppointmentDatabaseRepository(
    private val appointmentDao: AppointmentDao,
    private val ioCoroutineDispatcher: CoroutineDispatcher
) : AppointmentRepository {

    override suspend fun createAppointment(
        dateTime: Long,
        barberId: String,
        customerId: String
    ): Response<String> {
        return Response.Success(
            withContext(ioCoroutineDispatcher) {
                appointmentDao.insert(
                    AppointmentEntity(
                        id = "offline",
                        datetime = dateTime.toString(),
                        customerId = customerId,
                        barberId = barberId,
                        toSynchronize = true
                    )
                ).toString()
            }
        )
    }

    override suspend fun createAppointment(
        id: String,
        dateTime: Long,
        barberId: String,
        customerId: String
    ): Response<String> {
        return Response.Success(
            withContext(ioCoroutineDispatcher) {
                appointmentDao.insert(
                    AppointmentEntity(
                        id = id,
                        datetime = dateTime.toString(),
                        customerId = customerId,
                        barberId = barberId,
                        toSynchronize = false
                    )
                ).toString()
            }
        )
    }

    override fun listStreamCustomerAppointments(customerId: String): Flow<Response<List<Appointment>>> {
        return appointmentDao.observeByCustomerId(customerId)
            .flowOn(ioCoroutineDispatcher)
            .map {
                Response.Success(it.toModel())
            }
    }

    override fun listStreamBarberAppointments(barberId: String): Flow<Response<List<Appointment>>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAppointments(customerId: String): Response<List<Appointment>> {
        return Response.Success(
            withContext(ioCoroutineDispatcher) {
                appointmentDao.getByCustomerId(customerId).toModel()
            }
        )
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