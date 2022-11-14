package com.servall.data.remote

import com.servall.data.encrypt
import com.servall.data.entities.LoginDto
import com.servall.data.extensions.handleApiResponse
import com.servall.data.extensions.safeApiCall
import com.servall.data.toModel
import com.servall.domain.entities.Response
import com.servall.domain.entities.User
import com.servall.domain.repositories.LoginRepository
import java.security.MessageDigest

class LoginRemoteRepository(
    private val api: ApiCalls,
    private val sha256Generator: MessageDigest
) : LoginRepository {

    override suspend fun login(userName: String, password: String): Response<User> {
        return safeApiCall {
            val response = api.login(
                LoginDto(
                    userName = userName,
                    password = sha256Generator.encrypt(password.toByteArray())
                )
            )
            response.handleApiResponse { body -> body.toModel() }
        }
    }

    override suspend fun logout(userId: String): Response<Boolean> {
        return Response.Success(true)
    }

}