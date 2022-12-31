package com.servall.domain.repositories

import com.servall.domain.entities.User

interface UserRepository {
    suspend fun createUser(user: User)
    suspend fun createUsers(user: List<User>)
    suspend fun getUserById(userId: String): User
}