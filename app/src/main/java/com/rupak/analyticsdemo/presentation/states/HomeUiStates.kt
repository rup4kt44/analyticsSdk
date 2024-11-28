package com.rupak.analyticsdemo.presentation.states

import com.rupak.analyticssdk.domain.model.Event

data class HomeUiStates(
    val loading: Boolean = false,
    val currentlyActiveSession: String? = null,
    val sessionId: String? = null,
    val event: Event? = null,
    val eventList:List<Event>?= null
)