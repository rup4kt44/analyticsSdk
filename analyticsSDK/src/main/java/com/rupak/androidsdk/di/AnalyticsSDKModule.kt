package com.rupak.androidsdk.di


import android.app.Application
import androidx.room.Room
import com.rupak.androidsdk.data.local.AnalyticsDatabase
import com.rupak.androidsdk.data.local.EventDao
import com.rupak.androidsdk.data.local.SessionDao
import com.rupak.androidsdk.data.repository.AnalyticsRepository
import com.rupak.androidsdk.data.repository.AnalyticsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AnalyticsSDKModule {


    @Provides
    @Singleton
    fun provideAnalyticsDatabase(application: Application): AnalyticsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = AnalyticsDatabase::class.java,
            name = Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideEventDao(
        analyticsDatabase: AnalyticsDatabase
    ): EventDao = analyticsDatabase.eventDao()

    @Provides
    @Singleton
    fun provideSessionDao(
        analyticsDatabase: AnalyticsDatabase
    ): SessionDao = analyticsDatabase.sessionDao()

    @Provides
    @Singleton
    fun provideAnalyticsRepository(
        sessionDao: SessionDao,
        eventDao: EventDao
    ): AnalyticsRepository = AnalyticsRepositoryImpl(sessionDao = sessionDao, eventsDao = eventDao)
}

object Constants {
    const val DATABASE_NAME = "analytics_database"
}
