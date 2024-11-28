package com.rupak.analyticsdemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rupak.analyticsdemo.presentation.intents.HomeIntents
import com.rupak.analyticsdemo.presentation.states.HomeUiStates
import com.rupak.analyticsdemo.presentation.util.AnalyticsUtil.Event
import com.rupak.analyticssdk.data.repository.AnalyticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val analyticsRepository: AnalyticsRepository) :
    ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiStates())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        getEventList()
        getActiveSession()
    }

    private fun getEventList() {
        viewModelScope.launch {
            analyticsRepository.getEventList().collectLatest {
                _homeUiState.value = _homeUiState.value.copy(eventList = it)
            }
        }
    }

    private fun getActiveSession() {
        viewModelScope.launch {
            analyticsRepository.getActiveSession().collectLatest {
                it?.sessionId?.let { sessionId ->
                    _homeUiState.value = _homeUiState.value.copy(currentlyActiveSession = sessionId)
                }

            }
        }
    }

    fun handleIntents(homeIntents: HomeIntents) {
        when (homeIntents) {
            is HomeIntents.AddEvent -> {}
            is HomeIntents.OnSessionEndClick -> {
                stopSession(homeIntents.id)
            }

            HomeIntents.OnSessionStartClick -> {
                viewModelScope.launch {
                    val sessionId = analyticsRepository.startSession()
                    logEvent(sessionId)
                    _homeUiState.value = _homeUiState.value.copy(
                        sessionId = sessionId,
                        currentlyActiveSession = sessionId
                    )
                }
            }
        }
    }

    private fun stopSession(id: String) {
        viewModelScope.launch {
            val rowAffected = analyticsRepository.stopSession(id)

            if (rowAffected > 0) {
                _homeUiState.value = _homeUiState.value.copy(
                    currentlyActiveSession = null, sessionId = null
                )
            }
        }
    }

    private fun logEvent(id:String){
        viewModelScope.launch {
            analyticsRepository.logEvent(
                sessionId = id,
                name = Event.EVENT_SESSION_START_CLICK.eventName,
                properties = mapOf(Event.EVENT_SESSION_START_CLICK.eventName to "Session started with id : $id")
            )
        }
    }


}