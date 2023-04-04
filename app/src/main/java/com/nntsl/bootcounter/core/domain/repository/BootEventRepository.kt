package com.nntsl.bootcounter.core.domain.repository

import com.nntsl.bootcounter.core.domain.model.BootEvent
import kotlinx.coroutines.flow.Flow

interface BootEventRepository {

    val savedBootEvents: Flow<List<BootEvent>>

    suspend fun insertBootEvent(timestamp: Long)
}
