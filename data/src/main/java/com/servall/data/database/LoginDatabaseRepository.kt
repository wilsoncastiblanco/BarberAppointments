package com.servall.data.database

import com.servall.data.exceptions.RecordDoesNotExistException
import com.servall.data.toModel
import com.servall.domain.entities.Response
import com.servall.domain.entities.User
import com.servall.domain.repositories.LoginRepository

class LoginDatabaseRepository(private val userDao: UserDao) : LoginRepository {

    override suspend fun login(userName: String, password: String): Response<User> {
        return try {
            val user: User? = userDao.login(password = password, userName = userName)?.toModel()
            if (user != null) {
                Response.Success(user)
            } else {
                Response.Error(RecordDoesNotExistException())
            }
        } catch (exception: Exception) {
            Response.Error(exception)
        }
    }

    override suspend fun logout(userId: String): Response<Boolean> {
        TODO("Not yet implemented")
    }
}