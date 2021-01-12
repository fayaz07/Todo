package me.fayaz07.todo.db.dao

import androidx.room.TypeConverter
import me.fayaz07.todo.models.TodoStatus

public class Converters {
    @TypeConverter
    fun fromTodoStatus(value: TodoStatus): String {
        return value.name
    }

    @TypeConverter
    fun toTodoStatus(value: String): TodoStatus {
        if (value.contains("Pending")) return TodoStatus.Pending
        if (value.contains("Completed")) return TodoStatus.Completed
        if (value.contains("Lagging")) return TodoStatus.Lagging
        return TodoStatus.Pending
    }
}
