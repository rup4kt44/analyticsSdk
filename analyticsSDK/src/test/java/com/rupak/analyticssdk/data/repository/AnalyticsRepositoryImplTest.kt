package com.rupak.analyticssdk.data.repository

import com.google.gson.Gson
import com.rupak.analyticssdk.data.local.EventDao
import com.rupak.analyticssdk.data.local.SessionDao
import com.rupak.analyticssdk.domain.model.Event
import com.rupak.analyticssdk.domain.model.Session
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify


@ExperimentalCoroutinesApi
class AnalyticsRepositoryImplTest {

    lateinit var analyticsRepository: AnalyticsRepository

    @Mock
    private lateinit var mockSessionDao: SessionDao

    @Mock
    private lateinit var mockEventDao: EventDao


    @Before
    fun setUp() {
        mockSessionDao = Mockito.mock(SessionDao::class.java)
        mockEventDao = Mockito.mock(EventDao::class.java)

        // Instantiate the repository with mocked dependencies
        analyticsRepository = AnalyticsRepositoryImpl(mockSessionDao, mockEventDao)
    }

    @Test
    fun `should start the analytics session and return the session id provided`() {
        val expectedSessionId = "test-session-id"
        val session = Session(
            sessionId = expectedSessionId,
            startTime = System.currentTimeMillis(),
            endTime = null,
            isActive = true
        )
        runBlocking {
            Mockito.`when`(mockSessionDao.insertSession(session)).thenReturn(Unit)
            val sessionId = analyticsRepository.startSession(expectedSessionId)
            assertEquals(true, sessionId == expectedSessionId)
        }
    }

    @Test
    fun `stopSession should stop the session and return number of rows affected`() {
        val sessionId = "test-session-id"
        val expectedRowsAffected = 1
        runBlocking {
            Mockito.`when`(mockSessionDao.stopSession(sessionId)).thenReturn(expectedRowsAffected)
            val rowsAffected = analyticsRepository.stopSession(sessionId)
            assertEquals(expectedRowsAffected, rowsAffected)
            verify(mockSessionDao).stopSession(sessionId)
        }

    }

    @Test
    fun `log event should log the event into eventDAO`() {
        // Arrange
        val sessionId = "test-session-id"
        val eventName = "test_event"
        val eventProperties = mapOf("key1" to "value1", "key2" to 2)
        val event = Event(
            sessionId = sessionId,
            name = eventName,
            properties = Gson().toJson(eventProperties),
            timestamp = System.currentTimeMillis()
        )
        runBlocking {
            Mockito.`when`(mockEventDao.insertEvent(event)).thenReturn(Unit)

            analyticsRepository.logEvent(sessionId, eventName, eventProperties)

        }
    }

    @Test
    fun `getEventList should return the list of event saved in room`() {
        val eventList = listOf(
            Event(
                1,
                "event1",
                "Event 1",
                properties = Gson().toJson(mapOf("a" to "a")),
                timestamp = System.currentTimeMillis()
            ),
            Event(
                2,
                "event2",
                "Event 2",
                properties = Gson().toJson(mapOf("b" to "b")),
                timestamp = System.currentTimeMillis()
            ),
        )
        runBlocking {
            Mockito.`when`(mockEventDao.getEvents()).thenReturn(flowOf(eventList))
            val result = analyticsRepository.getEventList()
            result.collect { events ->
                assertEquals(eventList, events)
            }
        }

    }

    @Test
    fun `getActiveSession should return the active session`() {
        // Arrange
        val activeSession = Session(
            sessionId = "active-session-id",
            startTime = System.currentTimeMillis(),
            endTime = null,
            isActive = true
        )
        runBlocking {
            Mockito.`when`(mockSessionDao.getActiveSession()).thenReturn(flowOf(activeSession))
            val result = analyticsRepository.getActiveSession()
            result.collect { session ->
                assertEquals(activeSession, session)
            }
        }
    }


}