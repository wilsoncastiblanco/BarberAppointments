package com.servall.data.extensions

import com.servall.data.exceptions.RecordDoesNotExistException
import com.servall.domain.entities.Response

suspend fun <T : Any> safeQuery( content: suspend () -> T?): Response<T> {
    return try {
        val result: T? = content()
        if (result != null) {
            Response.Success(result)
        } else {
            Response.Error(RecordDoesNotExistException())
        }
    } catch (exception: Exception) {
        Response.Error(exception)
    }
}