package com.servall.data

import com.servall.domain.entities.Response
import com.servall.domain.entities.User
import com.servall.domain.repositories.LoginRepository

class LoginDataRepository: LoginRepository {

    override suspend fun login(userName: String, password: String): Response<User> {
        return Response.Success(null)
    }

    override suspend fun logout(userId: String): Response<Boolean> {
        TODO("Not yet implemented")
    }

}