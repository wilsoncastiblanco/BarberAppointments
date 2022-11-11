package com.servall.data.remote

import com.servall.data.entities.BarberResponse
import com.servall.data.entities.LoginDto
import com.servall.data.entities.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiCalls {

    @POST("barbershop/login")
    suspend fun login(@Body login: LoginDto): Response<LoginResponse>

    @GET("barbers")
    suspend fun listBarbers(
        @Query("weekDayRange") weekDayRange: String,
        @Query("hourRange") hourRange: String
    ): List<BarberResponse>

}