package com.servall.data

import com.servall.data.entities.LoginResponse
import com.servall.domain.entities.User

fun LoginResponse.toModel(): User {
    return User(
        id = this.userId,
        userName = this.userName,
        fullName = this.fullName,
        role = this.role
    )
}