package com.servall.domain.entities

data class User(
    val id: String,
    val userName: String,
    val fullName: String,
    val role: String,
    //FIXME: DON'T DO THIS!
    val password: String?
)