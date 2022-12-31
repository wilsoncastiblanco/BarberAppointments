package com.servall.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.servall.data.entities.AppointmentEntity
import com.servall.data.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        AppointmentEntity::class
    ],
    version = 2
)
abstract class BarbershopDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun appointmentDao(): AppointmentDao
}