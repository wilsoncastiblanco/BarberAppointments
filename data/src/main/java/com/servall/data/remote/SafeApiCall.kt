package com.servall.data.remote

import android.content.Context
import android.net.ConnectivityManager
import com.servall.data.exceptions.OfflineException
import com.servall.domain.entities.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SafeApiCall(
    val context: Context,
    private val ioCoroutineDispatcher: CoroutineDispatcher
) {
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    suspend operator fun <T : Any> invoke(call: suspend () -> Response<T>): Response<T> {

        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        return if (networkCapabilities == null) {
            Response.Error(OfflineException())
        } else {
            try {
                withContext(ioCoroutineDispatcher) {
                    call()
                }
            } catch (e: Exception) {
                Response.Error(e)
            }
        }
    }

}