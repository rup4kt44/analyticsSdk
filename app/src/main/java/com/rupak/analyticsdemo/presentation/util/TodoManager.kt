package com.rupak.analyticsdemo.presentation.util

import com.rupak.analyticsdemo.model.local.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object TodoManager {

    private val todoList = mutableListOf<Todo>()

    fun getAllTodo(): List<Todo> {
        return todoList
    }

    fun addTodo(title:String){
        todoList.add(Todo(
           title = title
        ))
    }
    fun deleteTodo(id: Int){
        todoList.removeIf {
            it.id ==id
        }
    }
}