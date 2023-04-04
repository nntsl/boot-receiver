package com.nntsl.bootcounter.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nntsl.bootcounter.core.database.model.BootEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BootEventDao {

    @Insert
    suspend fun insertBootEvent(bootEvent: BootEventEntity)

    @Query("SELECT * FROM boot_events")
    fun getAllBootEvents(): Flow<List<BootEventEntity>>

}
