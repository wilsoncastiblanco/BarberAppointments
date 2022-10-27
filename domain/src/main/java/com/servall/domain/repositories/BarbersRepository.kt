package com.servall.domain.repositories

import com.servall.domain.entities.Barber
import com.servall.domain.entities.Response

interface BarbersRepository {
    suspend fun listBarbers(weekDayRange: String, hourRange: String): Response<List<Barber>>
}