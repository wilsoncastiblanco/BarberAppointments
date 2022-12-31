package com.servall.data.mappers

import com.servall.data.entities.UserEntity
import com.servall.domain.entities.Barber
import com.servall.domain.entities.User

fun List<UserEntity>.toModel(): List<Barber> {
    return this.map {
        Barber(
            fullName = it.fullName,
            id = it.id
        )
    }
}

fun List<Barber>.toEntity(): List<User> {
    return this.map {
        User(
            id = it.id,
            fullName = it.fullName,
            userName = it.fullName,
            password = "",
            role = "BARBER"
        )
    }
}