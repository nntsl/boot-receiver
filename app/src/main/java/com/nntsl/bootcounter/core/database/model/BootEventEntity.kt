package com.nntsl.bootcounter.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nntsl.bootcounter.core.domain.model.BootEvent

@Entity(tableName = "boot_events")
data class BootEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long
)

fun BootEventEntity.asExternalModel() = BootEvent(
    id = id,
    timestamp = timestamp
)
