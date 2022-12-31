package com.servall.data.entities

import androidx.room.Embedded
import androidx.room.Relation

class AppointmentDto {
    @Embedded
    var appointmentEntity: AppointmentEntity? = null

    @Relation(entity = UserEntity::class, parentColumn = "customerId", entityColumn = "id")
    var customer: UserEntity? = null

    @Relation(entity = UserEntity::class, parentColumn = "barberId", entityColumn = "id")
    var barber: UserEntity? = null
}