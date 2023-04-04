package com.nntsl.bootcounter.core.data.repository

import com.nntsl.bootcounter.core.database.dao.BootEventDao
import com.nntsl.bootcounter.core.database.model.BootEventEntity
import com.nntsl.bootcounter.core.database.model.asExternalModel
import com.nntsl.bootcounter.core.domain.model.BootEvent
import com.nntsl.bootcounter.core.domain.repository.BootEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BootEventRepositoryImpl @Inject constructor(
    private val bootEventDao: BootEventDao
) : BootEventRepository {

    override val savedBootEvents: Flow<List<BootEvent>> = bootEventDao.getAllBootEvents()
        .map {
            it.map(BootEventEntity::asExternalModel)
        }

    override suspend fun insertBootEvent(timestamp: Long) {
        bootEventDao.insertBootEvent(
            BootEventEntity(timestamp = timestamp)
        )
    }
}
