package com.servall.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.servall.data.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class BarbershopDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}