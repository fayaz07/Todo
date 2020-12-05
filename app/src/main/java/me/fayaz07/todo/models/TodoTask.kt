package me.fayaz07.todo.models

import java.util.*

/*
 *
 * title : String
 * description : String
 * due on : Date
 * status: TodoTaskStatus
 */

data class TodoTask(
    val title: String,
    val description: String,
    val dueOn: Date,
    var status: TodoTaskStatus = TodoTaskStatus.Pending
)

enum class TodoTaskStatus { Pending, Completed }
