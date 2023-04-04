package com.nntsl.bootcounter.core.database.di

import com.nntsl.bootcounter.core.database.BootEventDatabase
import com.nntsl.bootcounter.core.database.dao.BootEventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideExchangeRatesDao(
        appDatabase: BootEventDatabase
    ): BootEventDao = appDatabase.bootEventDao()
}
