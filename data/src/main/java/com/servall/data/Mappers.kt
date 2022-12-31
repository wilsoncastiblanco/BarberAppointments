package com.servall.data

import com.servall.data.entities.AppointmentResponse
import com.servall.data.entities.BarberResponse
import com.servall.data.entities.LoginResponse
import com.servall.data.entities.UserEntity
import com.servall.domain.entities.Appointment
import com.servall.domain.entities.Barber
import com.servall.domain.entities.Customer
import com.servall.domain.entities.User

fun LoginResponse.toModel(): User {
    return User(
        id = this.id,
        userName = this.userName,
        fullName = this.fullName,
        role = this.role,
        password = this.password
    )
}

fun List<BarberResponse>.toModel(): List<Barber> {
    return this.map {
        Barber(
            fullName = it.fullName,
            id = it.id
        )
    }
}

fun AppointmentResponse.toModel(): Appointment {
    return Appointment(
        id = this.id,
        datetime = this.datetime,
        customer = this.customer,
        barber = this.barber
    )
}

fun UserEntity.toModel(): User {
    return User(
        id = this.id,
        userName = this.userName,
        fullName = this.fullName,
        role = this.role,
        password = this.password
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        userName = this.userName,
        fullName = this.fullName,
        password = this.password.orEmpty(),
        role  = this.role
    )
}

fun UserEntity.toCustomerModel(): Customer {
    return Customer(
        id = this.id,
        fullName = this.fullName,
    )
}

fun UserEntity.toBarberModel(): Barber {
    return Barber(
        id = this.id,
        fullName = this.fullName,
    )
}


