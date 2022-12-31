package com.servall.data.extensions

import com.servall.data.exceptions.NullBodyException
import com.servall.domain.entities.Response
import retrofit2.Response as ApiResponse

fun <E : Any, T : Any> ApiResponse<T>.handleApiResponse(map: (body: T) -> E): Response<E> {
    return if (this.isSuccessful) {
        val body = this.body()
        if (body != null) {
            try {
                val type = map(body)
                Response.Success(type)
            } catch (exception: Exception) {
                Response.Error(exception)
            }
        } else {
            Response.Error(NullBodyException())
        }
    } else {
        Response.Error(
            Exception("The server has returned an invalid response. ${this.errorBody().toString()}")
        )
    }
}

