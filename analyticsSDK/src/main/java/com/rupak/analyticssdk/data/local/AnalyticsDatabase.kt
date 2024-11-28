package com.rupak.analyticssdk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rupak.analyticssdk.domain.model.Event
import com.rupak.analyticssdk.domain.model.Session

@Database(entities = [Session::class, Event::class], version = 1 , exportSchema = false)
abstract class AnalyticsDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun eventDao(): EventDao
}