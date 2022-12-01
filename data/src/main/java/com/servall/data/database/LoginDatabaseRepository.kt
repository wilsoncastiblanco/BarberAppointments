package com.servall.data.database

import com.servall.data.extensions.safeQuery
import com.servall.data.toModel
import com.servall.domain.entities.Response
import com.servall.domain.entities.User
import com.servall.domain.repositories.LoginRepository

class LoginDatabaseRepository(private val userDao: UserDao) : LoginRepository {

    override suspend fun login(userName: String, password: String): Response<User> {
        return safeQuery { userDao.login(password = password, userName = userName)?.toModel() }
    }

    override suspend fun logout(userId: String): Response<Boolean> {
        TODO("Not yet implemented")
    }
}