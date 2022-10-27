package com.servall.domain.repositories

import com.servall.domain.entities.Response
import com.servall.domain.entities.User

interface LoginRepository {
    suspend fun login(userName: String, password: String): Response<User>
    suspend fun logout(userId: String): Response<Boolean>
}