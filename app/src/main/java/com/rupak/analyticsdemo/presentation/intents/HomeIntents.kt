package com.rupak.analyticsdemo.presentation.intents

import com.rupak.analyticssdk.domain.model.Event

sealed interface HomeIntents {

    data object OnSessionStartClick: HomeIntents
    data class OnSessionEndClick(val id :String):HomeIntents
    data class AddEvent(val event: Event):HomeIntents
}