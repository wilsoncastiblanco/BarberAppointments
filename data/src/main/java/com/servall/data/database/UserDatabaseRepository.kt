package com.servall.data.database

import com.servall.data.toEntity
import com.servall.data.toModel
import com.servall.domain.entities.User
import com.servall.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserDatabaseRepository(
    private val userDao: UserDao,
    private val ioCoroutineDispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun createUser(user: User) {
        withContext(ioCoroutineDispatcher) {
            userDao.insert(user.toEntity())
        }
    }

    override suspend fun createUsers(users: List<User>) {
        withContext(ioCoroutineDispatcher) {
            userDao.insert(users.map { it.toEntity() })
        }
    }

    override suspend fun getUserById(userId: String): User {
        return userDao.getById(userId).toModel()
    }

}