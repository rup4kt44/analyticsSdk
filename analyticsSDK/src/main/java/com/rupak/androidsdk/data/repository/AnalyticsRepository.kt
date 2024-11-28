package com.rupak.androidsdk.data.repository

import com.google.gson.Gson
import com.rupak.androidsdk.data.local.EventDao
import com.rupak.androidsdk.data.local.SessionDao
import com.rupak.androidsdk.domain.model.Event
import com.rupak.androidsdk.domain.model.Session
import jakarta.inject.Inject

interface AnalyticsRepository {

    suspend fun startSession():Long

    suspend fun stopSession(sessionId:Long)

    suspend fun logEvent(sessionId:Long, name:String , properties: Map<String,Any>)
}

class AnalyticsRepositoryImpl @Inject constructor(
    private  val sessionDao: SessionDao,
    private val eventsDao: EventDao
): AnalyticsRepository {
    override suspend fun startSession(): Long {
        val session = Session(startTime = System.currentTimeMillis(), endTime = null, isActive = true)
        sessionDao.insertSession(session)
        return session.id
    }

    override suspend fun stopSession(sessionId: Long) {
        val session = sessionDao.getActiveSession() ?: return
        sessionDao.updateSession(session.copy(endTime = System.currentTimeMillis(), isActive = false))
    }

    override suspend fun logEvent(sessionId: Long, name: String, properties: Map<String, Any>) {
        val event = Event(
            sessionId = sessionId,
            name = name,
            properties = Gson().toJson(properties), // convert Map to JSON
            timestamp = System.currentTimeMillis()
        )
        eventsDao.insertEvent(event)
    }

}