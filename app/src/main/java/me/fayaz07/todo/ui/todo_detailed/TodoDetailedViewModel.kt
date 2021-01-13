package me.fayaz07.todo.ui.todo_detailed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.fayaz07.todo.models.Todo
import me.fayaz07.todo.models.TodoStatus
import me.fayaz07.todo.repository.TodoRepository

class TodoDetailedViewModel(private val todoRepository: TodoRepository) : ViewModel() {

    var todo: Todo? = null

    fun markToDoAsCompleted() {
        viewModelScope.launch {
            todo?.let { todoRepository.markTodoAsDone(it) }
        }
    }

    fun updateToDo() {
        viewModelScope.launch {
            val t = todo!!
            t.completedOn = 0
            t.status = TodoStatus.Pending
            todoRepository.update(t)
        }
    }

    fun getToDo(id: String) =
         todoRepository.getToDo(id)


}
