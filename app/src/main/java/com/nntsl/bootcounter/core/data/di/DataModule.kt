package com.nntsl.bootcounter.core.data.di

import com.nntsl.bootcounter.core.data.repository.BootEventRepositoryImpl
import com.nntsl.bootcounter.core.domain.repository.BootEventRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsPinCodesRepository(
        bootEventRepository: BootEventRepositoryImpl
    ): BootEventRepository
}
