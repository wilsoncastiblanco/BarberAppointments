package com.servall.data

import com.servall.data.retrofit.ApiCalls
import com.servall.domain.entities.User


fun ApiCalls.LoginResponse.toDomainUser(): User {
    return User(
        id = this.userId,
        userName = this.userName,
        fullName = this.fullName,
        role = this.role
    )
}