package com.rupak.androidsdk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rupak.androidsdk.domain.model.Event

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    @Query("SELECT * FROM events WHERE sessionId = :sessionId")
    suspend fun getEventsForSession(sessionId: Long): List<Event>
}