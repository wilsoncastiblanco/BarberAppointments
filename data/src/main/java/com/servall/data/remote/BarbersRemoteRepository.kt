package com.servall.data.remote

import com.servall.domain.entities.Barber
import com.servall.domain.entities.Response
import com.servall.domain.repositories.BarbersRepository

class BarbersRemoteRepository: BarbersRepository {
    override suspend fun listBarbers(
        weekDayRange: String,
        hourRange: String
    ): Response<List<Barber>> {
        TODO("Not yet implemented")
    }
}