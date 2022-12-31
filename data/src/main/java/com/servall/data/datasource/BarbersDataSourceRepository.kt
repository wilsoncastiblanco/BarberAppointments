package com.servall.data.datasource

import com.servall.data.database.BarberDatabaseRepository
import com.servall.data.database.UserDatabaseRepository
import com.servall.data.exceptions.OfflineException
import com.servall.data.mappers.toEntity
import com.servall.data.remote.BarbersRemoteRepository
import com.servall.domain.entities.Barber
import com.servall.domain.entities.Response
import com.servall.domain.repositories.BarbersRepository

class BarbersDataSourceRepository(
    private val barbersRemoteRepository: BarbersRemoteRepository,
    private val barbersDatabaseRepository: BarberDatabaseRepository,
    private val userDatabaseRepository: UserDatabaseRepository
): BarbersRepository {
    override suspend fun listBarbers(
        weekDayRange: String,
        hourRange: String
    ): Response<List<Barber>> {
        return when(val resultRemote = barbersRemoteRepository.listBarbers(weekDayRange, hourRange)) {
            is Response.Error -> {
                if (resultRemote.exception is OfflineException) {
                    barbersDatabaseRepository.listBarbers(weekDayRange, hourRange)
                } else {
                    resultRemote
                }
            }
            is Response.Success -> {
                userDatabaseRepository.createUsers(resultRemote.data.toEntity())
                barbersDatabaseRepository.listBarbers(weekDayRange, hourRange)
            }
        }
    }
}