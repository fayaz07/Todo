package me.fayaz07.todo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.fayaz07.todo.db.dao.Converters
import me.fayaz07.todo.models.Todo
import me.fayaz07.todo.db.dao.TodoDao

@Database(entities = [Todo::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
