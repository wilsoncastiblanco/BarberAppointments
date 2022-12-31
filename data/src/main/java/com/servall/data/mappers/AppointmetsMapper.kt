package com.servall.data.mappers

import com.servall.data.entities.AppointmentDto
import com.servall.data.entities.AppointmentEntity
import com.servall.data.toBarberModel
import com.servall.data.toCustomerModel
import com.servall.domain.entities.Appointment

fun List<AppointmentDto>.toModel(): List<Appointment> {
    return this.map {
        Appointment(
            id = it.appointmentEntity!!.id,
            datetime = it.appointmentEntity!!.datetime.toLong(),
            customer = it.customer?.toCustomerModel()!!,
            barber = it.barber?.toBarberModel()!!
        )
    }
}

fun Appointment.toEntity(synchronized: Boolean): AppointmentEntity {
    return AppointmentEntity(
        id = this.id,
        datetime = this.datetime.toString(),
        customerId = this.customer.id,
        barberId = this.barber.id,
        toSynchronize = synchronized
    )
}

fun List<Appointment>.toEntity(synchronized: Boolean): List<AppointmentEntity> {
    return this.map {
        AppointmentEntity(
            id = it.id,
            datetime = it.datetime.toString(),
            customerId = it.customer.id,
            barberId = it.barber.id,
            toSynchronize = synchronized
        )
    }
}