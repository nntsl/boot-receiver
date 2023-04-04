package com.nntsl.bootcounter.core.domain.usecase

import com.nntsl.bootcounter.core.domain.repository.BootEventRepository
import javax.inject.Inject

class InsertBootEventUseCase @Inject constructor(
    private val repository: BootEventRepository
) {
    suspend operator fun invoke(timestamp: Long) {
        repository.insertBootEvent(timestamp)
    }
}
