package com.servall.data.di

import androidx.room.Room
import com.servall.data.database.BarbershopDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DB_BARBERSHOP = "barbershop_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), BarbershopDatabase::class.java, DB_BARBERSHOP)
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<BarbershopDatabase>().userDao() }
    single { get<BarbershopDatabase>().appointmentDao() }
}