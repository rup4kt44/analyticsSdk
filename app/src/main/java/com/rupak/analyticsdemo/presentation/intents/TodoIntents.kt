package com.rupak.analyticsdemo.presentation.intents

import com.rupak.analyticssdk.domain.model.Event

sealed interface TodoIntents {

    data class OnAddClick(val title:String): TodoIntents
    data class OnDeleteClick(val index:Int):TodoIntents
    data class  enableAnalytics(val isAnalyticsEnabled : Boolean) : TodoIntents
}