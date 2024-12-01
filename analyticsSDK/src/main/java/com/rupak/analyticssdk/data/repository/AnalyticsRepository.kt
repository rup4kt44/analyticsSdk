package com.rupak.analyticssdk.data.repository

import com.google.gson.Gson
import com.rupak.analyticssdk.data.local.EventDao
import com.rupak.analyticssdk.data.local.SessionDao
import com.rupak.analyticssdk.domain.model.Event
import com.rupak.analyticssdk.domain.model.Session
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface AnalyticsRepository {

    suspend fun startSession(sessionId: String? = null): String

    suspend fun stopSession(sessionId: String): Int

    suspend fun logEvent(sessionId: String, name: String, properties: Map<String, Any>)

    suspend fun getEventList(): Flow<List<Event>>

    suspend fun getActiveSession(): Flow<Session?>

}

class AnalyticsRepositoryImpl @Inject constructor(
    private val sessionDao: SessionDao, private val eventsDao: EventDao
) : AnalyticsRepository {
    override suspend fun startSession(sessionId: String?): String {
        val session = Session(
            sessionId = sessionId ?: UUID.randomUUID().toString(),
            startTime = System.currentTimeMillis(),
            endTime = null,
            isActive = true
        )
        sessionDao.insertSession(session)
        return session.sessionId
    }

    override suspend fun stopSession(sessionId: String): Int {
        return sessionDao.stopSession(sessionId)
    }

    override suspend fun logEvent(sessionId: String, name: String, properties: Map<String, Any>) {
        val event = Event(
            sessionId = sessionId,
            name = name,
            properties = Gson().toJson(properties), // convert Map to JSON
            timestamp = System.currentTimeMillis()
        )
        eventsDao.insertEvent(event)
    }

    override suspend fun getEventList(): Flow<List<Event>> {
        return eventsDao.getEvents()
    }

    override suspend fun getActiveSession(): Flow<Session?> {
        return sessionDao.getActiveSession()
    }

}