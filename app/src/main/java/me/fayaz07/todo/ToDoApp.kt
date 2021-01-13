package me.fayaz07.todo

import android.app.Application
import me.fayaz07.todo.db.AppDatabase

class ToDoApp : Application() {

    val todoDao by lazy {
        AppDatabase.getInstance(applicationContext).todoDao()
    }
}
