package com.rupak.androidsdk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rupak.androidsdk.domain.model.Session

@Dao
interface SessionDao {
    @Insert
    suspend fun insertSession(session: Session)

    @Update
    suspend fun updateSession(session: Session)

    @Query("SELECT * FROM sessions WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveSession(): Session?
}