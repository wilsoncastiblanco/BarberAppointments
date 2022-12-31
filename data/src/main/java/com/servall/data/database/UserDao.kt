package com.servall.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.servall.data.entities.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<UserEntity>)

    @Query("SELECT * FROM User WHERE id = :userId")
    suspend fun getById(userId: String): UserEntity

    @Query("SELECT * FROM User WHERE role = :role")
    suspend fun getByRole(role: String): List<UserEntity>

    @Update
    fun update(userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)

    @Query("SELECT * FROM User WHERE userName = :userName AND password = :password LIMIT 1")
    suspend fun login(password: String, userName: String): UserEntity?

}