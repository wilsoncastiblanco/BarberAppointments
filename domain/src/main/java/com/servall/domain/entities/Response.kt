package com.servall.domain.entities

sealed class Response<out T : Any> {
    data class Error(val exception: Exception) : Response<Nothing>()
    data class Success<out T : Any>(
        val data: T?
    ) : Response<T>()
}
