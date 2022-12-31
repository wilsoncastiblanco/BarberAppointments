package com.servall.data.entities

data class LoginDto(
    val userName: String,
    val password: String
)

data class LoginResponse(
    val id: String,
    val fullName: String,
    val userName: String,
    val role: String,
    val password: String?
)