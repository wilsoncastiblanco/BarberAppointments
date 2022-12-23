package com.servall.data.remote

import com.servall.data.extensions.handleApiResponse
import com.servall.data.toModel
import com.servall.domain.entities.Barber
import com.servall.domain.entities.Response
import com.servall.domain.repositories.BarbersRepository

class BarbersRemoteRepository(
    private val api: ApiCalls,
    private val safeApiCall: SafeApiCall
) : BarbersRepository {

    override suspend fun listBarbers(
        weekDayRange: String,
        hourRange: String
    ): Response<List<Barber>> {
        return safeApiCall {
            val response = api.listBarbers(weekDayRange, hourRange)
            response.handleApiResponse { body -> body.toModel() }
        }
    }
}