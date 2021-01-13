package me.fayaz07.todo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.fayaz07.todo.ToDoApp
import me.fayaz07.todo.repository.TodoRepository
import me.fayaz07.todo.ui.add_todo.AddTodoViewModel
import me.fayaz07.todo.ui.home.HomeViewModel
import me.fayaz07.todo.ui.todo_detailed.TodoDetailedViewModel

class ViewModelFactory(val app: ToDoApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(TodoRepository(app.todoDao)) as T
            AddTodoViewModel::class.java -> AddTodoViewModel(TodoRepository(app.todoDao)) as T
            TodoDetailedViewModel::class.java -> TodoDetailedViewModel(TodoRepository(app.todoDao)) as T
            else -> throw Exception(modelClass.simpleName)
        }
    }
}
