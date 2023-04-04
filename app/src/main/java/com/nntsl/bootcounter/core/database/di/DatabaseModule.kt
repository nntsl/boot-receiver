package com.nntsl.bootcounter.core.database.di

import android.content.Context
import androidx.room.Room
import com.nntsl.bootcounter.core.database.BootEventDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesMyFinDatabase(
        @ApplicationContext context: Context,
    ): BootEventDatabase = Room.databaseBuilder(
        context,
        BootEventDatabase::class.java,
        "boot_event_database"
    ).build()
}
