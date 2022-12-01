package com.servall.data

import com.servall.data.entities.LoginResponse
import com.servall.data.entities.UserEntity
import com.servall.domain.entities.User

fun LoginResponse.toModel(): User {
    return User(
        id = this.userId,
        userName = this.userName,
        fullName = this.fullName,
        role = this.role,
        password = this.password
    )
}

fun UserEntity.toModel(): User {
    return User(
        id = this.userId,
        userName = this.userName,
        fullName = this.fullName,
        role = this.role,
        password = this.password
    )
}

fun User.toDb(): UserEntity {
    return UserEntity(
        userId = this.id,
        userName = this.userName,
        fullName = this.fullName,
        password = this.password.orEmpty()
    )
}