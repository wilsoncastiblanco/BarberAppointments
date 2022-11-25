package com.example.barberappointments

import android.app.Application
import com.servall.data.di.dataModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class BarberAppointmentsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@BarberAppointmentsApplication)
            // Load modules
            modules(dataModules(isDebug = BuildConfig.DEBUG))
        }
    }

}