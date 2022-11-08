package com.servall.data.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiCalls {

    @POST("barbershop/login")
    suspend fun login(@Body user: User): Response<LoginResponse>

    data class User(val userName: String, val password: String)
    data class LoginResponse(
        val userId: String,
        val fullName: String,
        val userName: String,
        val role: String
    )

    @GET("barbers")
    suspend fun listBarbers(
        @Query("weekDayRange") weekDayRange: String,
        @Query("hourRange") hourRange: String
    ): List<Barber>

    data class Barber(val userId: String, val fullName: String, val userName: String)
}