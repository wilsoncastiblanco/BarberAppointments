package com.servall.data

import com.servall.data.database.LoginDatabaseRepository
import com.servall.data.remote.LoginRemoteRepository
import com.servall.domain.entities.Response
import com.servall.domain.entities.User
import com.servall.domain.repositories.LoginRepository

class LoginHybridRepository(
    private val loginDatabaseRepository: LoginDatabaseRepository,
    private val loginRemoteRepository: LoginRemoteRepository
) : LoginRepository {

    override suspend fun login(userName: String, password: String): Response<User> {
        return when (val response = loginDatabaseRepository.login(userName, password)) {
            is Response.Error -> loginRemoteRepository.login(userName, password)
            is Response.Success -> response
        }
    }

    override suspend fun logout(userId: String): Response<Boolean> {
        TODO("Not yet implemented")
    }

}