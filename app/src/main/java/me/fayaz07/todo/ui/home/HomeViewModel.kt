package me.fayaz07.todo.ui.home

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.fayaz07.todo.models.TodoTask
import me.fayaz07.todo.repository.TodoRepository
import me.fayaz07.todo.ui.todo_detailed.TodoDetailedActivity

class HomeViewModel : ViewModel() {

    private lateinit var ctx: Context

    val todoListLiveData: LiveData<List<TodoTask>> get() = TodoRepository.todoListLiveData

    fun setContext(context: Context) {
        ctx = context
    }

}
