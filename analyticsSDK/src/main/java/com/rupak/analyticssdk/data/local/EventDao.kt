package com.rupak.analyticssdk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rupak.analyticssdk.domain.model.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    @Query("SELECT * FROM events WHERE sessionId = :sessionId")
     fun getEventsForSession(sessionId: Long): Flow<List<Event>>

    @Query("SELECT * FROM events")
    fun getEvents(): Flow<List<Event>>
}