package com.nntsl.bootcounter.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nntsl.bootcounter.core.database.dao.BootEventDao
import com.nntsl.bootcounter.core.database.model.BootEventEntity

@Database(
    entities = [BootEventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BootEventDatabase : RoomDatabase() {

    abstract fun bootEventDao(): BootEventDao
}
