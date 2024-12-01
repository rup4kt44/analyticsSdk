package com.rupak.analyticsdemo.presentation.states

import com.rupak.analyticsdemo.model.local.Todo

data class TodoUiStates(
    val todoList: List<Todo>? = null,
    val isAnalyticsEnabled : Boolean = false,
    val analyticsSessionId:String? = null
)