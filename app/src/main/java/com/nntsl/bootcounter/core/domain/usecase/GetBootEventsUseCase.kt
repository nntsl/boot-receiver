package com.nntsl.bootcounter.core.domain.usecase

import com.nntsl.bootcounter.core.domain.model.BootEvent
import com.nntsl.bootcounter.core.domain.repository.BootEventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBootEventsUseCase @Inject constructor(
    private val repository: BootEventRepository
) {
    operator fun invoke(): Flow<List<BootEvent>> {
        return repository.savedBootEvents
    }
}
