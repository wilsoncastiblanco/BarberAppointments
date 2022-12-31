package com.servall.data.datasource

import com.servall.data.database.LoginDatabaseRepository
import com.servall.data.database.UserDatabaseRepository
import com.servall.data.exceptions.OfflineException
import com.servall.data.remote.LoginRemoteRepository
import com.servall.domain.entities.Response
import com.servall.domain.entities.User
import com.servall.domain.repositories.LoginRepository

class LoginDataSourceRepository(
    private val loginDatabaseRepository: LoginDatabaseRepository,
    private val loginRemoteRepository: LoginRemoteRepository,
    private val userDatabaseRepository: UserDatabaseRepository
) : LoginRepository {

    override suspend fun login(userName: String, password: String): Response<User> {
        return when (val remoteResult = loginRemoteRepository.login(userName, password)) {
            is Response.Error -> remoteResult
            is Response.Success -> {
                userDatabaseRepository.createUser(remoteResult.data)
                Response.Success(userDatabaseRepository.getUserById(remoteResult.data.id))
            }
        }
    }

    override suspend fun logout(userId: String): Response<Boolean> {
        TODO("Not yet implemented")
    }

}