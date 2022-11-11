package com.servall.data.remote

import com.servall.data.encrypt
import com.servall.data.entities.LoginDto
import com.servall.data.exceptions.NullBodyException
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
        try {
            val response = api.login(
                LoginDto(
                    userName = userName,
                    password = sha256Generator.encrypt(password.toByteArray())
                )
            )
            return if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Response.Success(body.toModel())
                } else {
                    Response.Error(NullBodyException())
                }
            } else {
                Response.Error(Exception("The server has returned an invalid response. ${response.errorBody().toString()}"))
            }
        } catch (e: Exception) {
            return Response.Error(e)
        }
    }

    override suspend fun logout(userId: String): Response<Boolean> {
        return Response.Success(true)
    }

}