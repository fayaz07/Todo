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

    lateinit var ctx: Context

    val todoListLiveData: LiveData<List<TodoTask>> get() = TodoRepository.todoListLiveData

    fun setContext(context: Context) {
        ctx = context
    }

    val handleTodoTask: (TodoTask) -> Unit = {
        println(it.title)
        var intent = Intent(ctx, TodoDetailedActivity::class.java)
        intent.putExtra("todoId", it.id)
        startActivity(ctx, intent, null)
    }
}
