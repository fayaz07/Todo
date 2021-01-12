package me.fayaz07.todo.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.fayaz07.todo.models.Todo
import me.fayaz07.todo.repository.TodoRepository

class HomeViewModel : ViewModel() {

    private lateinit var ctx: Context

    val todoListLiveData: LiveData<List<Todo>> get() = TodoRepository.todoListLiveData

    fun setContext(context: Context) {
        ctx = context
    }

}
