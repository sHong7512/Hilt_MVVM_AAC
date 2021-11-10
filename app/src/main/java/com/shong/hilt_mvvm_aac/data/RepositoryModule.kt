package com.shong.hilt_mvvm_aac.data

import android.content.Context
import androidx.room.Room
import com.shong.hilt_mvvm_aac.data.db.AppDatabase
import com.shong.hilt_mvvm_aac.data.db.LogDao
import com.shong.hilt_mvvm_aac.handler.LoggerInMemory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideLogDao(database: AppDatabase): LogDao {
        return database.logDao()
    }

    @Singleton
    @Provides
    fun provideMemoryLog() : LoggerInMemory{
        return LoggerInMemory()
    }

    @Singleton
    @Provides
    fun provideRepository(logDao: LogDao, loggerInMemory: LoggerInMemory, @ApplicationContext context: Context) : Repository{
        return Repository(logDao, loggerInMemory, context)
    }

}