package com.servall.data.retrofit

import com.servall.domain.entities.Barber
import com.servall.domain.entities.Response
import com.servall.domain.repositories.BarbersRepository

class BarbersRepository: BarbersRepository {
    override suspend fun listBarbers(
        weekDayRange: String,
        hourRange: String
    ): Response<List<Barber>> {
        TODO("Not yet implemented")
    }
}