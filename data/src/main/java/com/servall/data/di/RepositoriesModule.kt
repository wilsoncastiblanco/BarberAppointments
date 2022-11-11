package com.servall.data.di

import com.servall.data.remote.LoginRemoteRepository
import org.koin.dsl.module

val repositoriesModule = module {
    factory { LoginRemoteRepository(api = get(), sha256Generator = get()) }
}