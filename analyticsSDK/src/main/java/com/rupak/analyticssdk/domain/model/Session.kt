package com.rupak.analyticssdk.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId:String,
    val startTime: Long,
    val endTime: Long?,
    val isActive: Boolean
)