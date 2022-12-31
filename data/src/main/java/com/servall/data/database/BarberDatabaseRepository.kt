package com.servall.data.database

import com.servall.data.mappers.toModel
import com.servall.domain.entities.Barber
import com.servall.domain.entities.Response
import com.servall.domain.repositories.BarbersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BarberDatabaseRepository(
    private val userDao: UserDao,
    private val ioCoroutineDispatcher: CoroutineDispatcher
) : BarbersRepository {

    override suspend fun listBarbers(
        weekDayRange: String,
        hourRange: String
    ): Response<List<Barber>> {
        return Response.Success(
            withContext(ioCoroutineDispatcher) {
                userDao.getByRole("BARBER").toModel()
            }
        )
    }
}