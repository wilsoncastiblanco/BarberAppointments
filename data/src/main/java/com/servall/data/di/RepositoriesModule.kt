package com.servall.data.di

import com.servall.data.LoginHybridRepository
import com.servall.data.database.LoginDatabaseRepository
import com.servall.data.remote.AppointmentRemoteRepository
import com.servall.data.remote.BarbersRemoteRepository
import com.servall.data.remote.LoginRemoteRepository
import org.koin.dsl.module

val repositoriesModule = module {
    factory { LoginRemoteRepository(api = get(), sha256Generator = get(), safeApiCall = get()) }
    factory { LoginDatabaseRepository(userDao = get()) }
    factory { LoginHybridRepository(loginDatabaseRepository = get(), loginRemoteRepository = get()) }
    factory { BarbersRemoteRepository(api = get(), safeApiCall = get()) }
    factory { AppointmentRemoteRepository(api = get(), safeApiCall = get()) }
}