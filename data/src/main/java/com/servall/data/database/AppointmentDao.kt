package com.servall.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.servall.data.entities.AppointmentDto
import com.servall.data.entities.AppointmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(appointmentEntity: AppointmentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(appointmentEntity: List<AppointmentEntity>): List<Long>

    @Query("SELECT * FROM Appointment WHERE customerId = :customerId")
    fun observeByCustomerId(customerId: String): Flow<List<AppointmentDto>>

    @Query("SELECT * FROM Appointment WHERE customerId = :customerId")
    fun getByCustomerId(customerId: String): List<AppointmentDto>
}