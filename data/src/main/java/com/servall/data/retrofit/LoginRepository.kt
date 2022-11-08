package com.servall.data.retrofit

import com.servall.data.toDomainUser
import com.servall.domain.entities.Response
import com.servall.domain.entities.User
import com.servall.domain.repositories.LoginRepository
import retrofit2.Call
import retrofit2.Retrofit
import java.security.MessageDigest

class LoginRepository : LoginRepository {

    override suspend fun login(userName: String, password: String): Response<User> {
        try {
            val sha256Generator = MessageDigest.getInstance("SHA-256")
            sha256Generator.update(password.toByteArray())
            val passwordSha256Hex = sha256Generator.digest().toString()
            val retrofit = Retrofit
                .Builder()
                .baseUrl("http://appointmentsapi-env.eba-kxrrzgb3.us-west-2.elasticbeanstalk.com/")
                .build()
            val loginApi = retrofit.create(ApiCalls::class.java)
            val response = loginApi.login(
                ApiCalls.User(
                    userName = userName,
                    password = passwordSha256Hex
                )
            )
            return if (response.isSuccessful) {
                val body = response.body()!!
                Response.Success(body.toDomainUser())
            } else {
                Response.Error(Exception("The server has returned an invalid response."))
            }
        } catch (e: Exception) {
            return Response.Error(e)
        }
    }

    override suspend fun logout(userId: String): Response<Boolean> {
        return Response.Success(true)
    }

}