package com.rupak.analyticsdemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rupak.analyticsdemo.presentation.intents.TodoIntents
import com.rupak.analyticsdemo.presentation.states.TodoUiStates
import com.rupak.analyticsdemo.presentation.util.TodoManager
import com.rupak.analyticssdk.data.repository.AnalyticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val analyticsRepository: AnalyticsRepository) :
    ViewModel() {
    private val _todoUiState = MutableStateFlow(TodoUiStates())
    val todoUiState = _todoUiState.asStateFlow()

    init {
        getAllTodoList()
    }

    fun handleIntent(intents: TodoIntents) {

        when (intents) {
            is TodoIntents.OnAddClick -> {
                addTodo(intents.title)
            }

            is TodoIntents.OnDeleteClick -> {
                deleteTodo(intents.index)
            }

            is TodoIntents.enableAnalytics -> {
                _todoUiState.value =
                    _todoUiState.value.copy(isAnalyticsEnabled = intents.isAnalyticsEnabled)
                enableOrDisableAnalytics()
            }
        }

    }

    private fun enableOrDisableAnalytics() {
        viewModelScope.launch {
            if (todoUiState.value.isAnalyticsEnabled) {
                val sessionId = analyticsRepository.startSession()
                _todoUiState.value = _todoUiState.value.copy(analyticsSessionId = sessionId)
            } else {
                _todoUiState.value.analyticsSessionId?.let {
                    analyticsRepository.stopSession(it)
                }

            }
        }
    }

    private fun addTodo(title: String) {
        TodoManager.addTodo(title = title)
        getAllTodoList()
        logToLocalAnalytics(title, mapOf("ON_CLICK" to "ADD"))

    }

    private fun logToLocalAnalytics(name: String, properties: Map<String, Any>) {
        viewModelScope.launch {
            if (isAnalyticsEnabled()) {
                todoUiState.value.analyticsSessionId?.let {
                    analyticsRepository.logEvent(
                        sessionId = it,
                        name = name,
                        properties = properties
                    )
                }
            }
        }
    }

    private fun deleteTodo(id: Int) {
        TodoManager.deleteTodo(id)
        getAllTodoList()
        logToLocalAnalytics("DELETE", mapOf("ON_CLICK" to "DELETE_ICON"))
    }

    private fun getAllTodoList() {
        _todoUiState.value = _todoUiState.value.copy(
            todoList = TodoManager.getAllTodo().reversed()
        )
    }

    private fun isAnalyticsEnabled(): Boolean {
        return todoUiState.value.isAnalyticsEnabled
    }

}