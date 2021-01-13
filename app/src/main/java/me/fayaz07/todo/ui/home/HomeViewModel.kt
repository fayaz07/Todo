package me.fayaz07.todo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.fayaz07.todo.models.Todo
import me.fayaz07.todo.models.TodoStatus
import me.fayaz07.todo.repository.TodoRepository

class HomeViewModel(private val todoRepository: TodoRepository) : ViewModel() {

    fun markTodoDone(todo: Todo) {
        viewModelScope.launch {
            val newTodo =
                todo.copy(
                    completedOn = System.currentTimeMillis(),
                    status = TodoStatus.Completed
                )
            todoRepository.markTodoAsDone(newTodo)
        }
    }

    val todoListLiveData: LiveData<List<Todo>> = todoRepository.getList()


}
