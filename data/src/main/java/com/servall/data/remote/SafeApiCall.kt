package com.servall.data.remote

import android.content.Context
import android.net.ConnectivityManager
import com.servall.data.exceptions.OfflineException
import com.servall.domain.entities.Response

class SafeApiCall(
    val context: Context
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
                call()
            } catch (e: Exception) {
                Response.Error(e)
            }
        }
    }

}