package me.fayaz07.todo.models

import java.util.*

/*
 *
 * title : String
 * description : String
 * due on : Date
 * priority : TodoTaskPriority
 *
 */

data class TodoTask(
    val title: String,
    val description: String,
    val dueOn: Date,
    val priority: TodoTaskPriority = TodoTaskPriority.Low,
)

enum class TodoTaskPriority { Low, Medium, High }
