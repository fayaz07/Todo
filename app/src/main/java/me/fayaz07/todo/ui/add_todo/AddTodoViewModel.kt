package me.fayaz07.todo.ui.add_todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.fayaz07.todo.models.TodoTask
import me.fayaz07.todo.repository.TodoRepository

class AddTodoViewModel : ViewModel() {

    val todoListLiveData: LiveData<List<TodoTask>> get() = TodoRepository.todoListLiveData

    fun newTodo(todoTask: TodoTask) {
        TodoRepository.addTodo(todoTask)
    }
}
