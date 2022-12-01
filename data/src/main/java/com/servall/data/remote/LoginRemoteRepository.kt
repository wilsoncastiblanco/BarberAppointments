package com.servall.data.remote

import com.servall.data.extensions.encrypt
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
                    password = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3"
                )
            )
            response.handleApiResponse { body -> body.toModel() }
        }
    }

    override suspend fun logout(userId: String): Response<Boolean> {
        return Response.Success(true)
    }

}