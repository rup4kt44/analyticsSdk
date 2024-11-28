package com.rupak.androidsdk.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val name: String,
    val properties: String, // JSON string representation of the properties
    val timestamp: Long
)