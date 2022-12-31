package com.servall.data.di

import com.servall.data.database.AppointmentDatabaseRepository
import com.servall.data.database.BarberDatabaseRepository
import com.servall.data.datasource.LoginDataSourceRepository
import com.servall.data.database.LoginDatabaseRepository
import com.servall.data.database.UserDatabaseRepository
import com.servall.data.datasource.AppointmentsDataSourceRepository
import com.servall.data.datasource.BarbersDataSourceRepository
import com.servall.data.remote.AppointmentRemoteRepository
import com.servall.data.remote.BarbersRemoteRepository
import com.servall.data.remote.LoginRemoteRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoriesModule = module {
    factory { UserDatabaseRepository(userDao = get(), ioCoroutineDispatcher = get(named("Dispatchers.IO"))) }
    factory { LoginRemoteRepository(api = get(), sha256Generator = get(), safeApiCall = get()) }
    factory { LoginDatabaseRepository(userDao = get()) }
    factory { LoginDataSourceRepository(loginDatabaseRepository = get(), loginRemoteRepository = get(), userDatabaseRepository = get()) }
    factory { AppointmentRemoteRepository(api = get(), safeApiCall = get()) }
    factory { AppointmentDatabaseRepository(appointmentDao = get(), ioCoroutineDispatcher = get(named("Dispatchers.IO"))) }
    factory { AppointmentsDataSourceRepository(appointmentRemoteRepository = get(), appointmentsDatabaseRepository = get(), userDatabaseRepository = get()) }
    factory { BarbersRemoteRepository(api = get(), safeApiCall = get()) }
    factory { BarberDatabaseRepository(userDao = get(), ioCoroutineDispatcher = get(named("Dispatchers.IO"))) }
    factory { BarbersDataSourceRepository(barbersRemoteRepository = get(), barbersDatabaseRepository = get(), userDatabaseRepository = get()) }
}