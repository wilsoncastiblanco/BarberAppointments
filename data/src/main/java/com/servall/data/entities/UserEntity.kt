package com.servall.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey val id: String = "",
    val userName: String = "",
    val fullName: String = "",
    val password: String = "",
    val role: String = ""
)