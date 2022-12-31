package com.servall.data.di

import com.servall.data.remote.ApiCalls
import com.servall.data.remote.SafeApiCall
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest

private const val BASE_URL =
    "http://appointmentsapi-env.eba-kxrrzgb3.us-west-2.elasticbeanstalk.com/"

internal fun networkModule(isDebug: Boolean) = module {
    single { okHttp(isDebug) }
    single { retrofit(get()) }
    single { MessageDigest.getInstance("SHA-256") }
    single { get<Retrofit>().create(ApiCalls::class.java) }
    single(named("Dispatchers.IO")) { Dispatchers.IO }
    single { SafeApiCall(context = androidContext(), ioCoroutineDispatcher = get(named("Dispatchers.IO"))) }
}

private fun okHttp(isDebug: Boolean): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor(isDebug))
    .build()

private fun retrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private fun loggingInterceptor(isDebug: Boolean) = HttpLoggingInterceptor().apply {
    if (isDebug) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
}