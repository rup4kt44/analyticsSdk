package com.rupak.androidsdk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rupak.androidsdk.data.local.EventDao
import com.rupak.androidsdk.data.local.SessionDao
import com.rupak.androidsdk.domain.model.Event
import com.rupak.androidsdk.domain.model.Session

@Database(entities = [Session::class, Event::class], version = 1 , exportSchema = false)
abstract class AnalyticsDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun eventDao(): EventDao
}