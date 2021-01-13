package me.fayaz07.todo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.fayaz07.todo.db.dao.Converters
import me.fayaz07.todo.db.dao.TodoDao
import me.fayaz07.todo.models.Todo

@Database(entities = [Todo::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "todo.db"
                    ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
