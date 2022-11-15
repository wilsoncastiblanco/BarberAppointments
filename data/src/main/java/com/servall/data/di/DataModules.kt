package com.servall.data.di

import org.koin.dsl.module

fun dataModules(isDebug: Boolean) = module {
    includes(
        networkModule(isDebug),
        repositoriesModule,
        databaseModule
    )
}