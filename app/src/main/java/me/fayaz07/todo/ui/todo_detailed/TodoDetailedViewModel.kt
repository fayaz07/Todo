package me.fayaz07.todo.ui.todo_detailed

import androidx.lifecycle.ViewModel
import me.fayaz07.todo.models.Todo
import me.fayaz07.todo.repository.TodoRepository

class TodoDetailedViewModel : ViewModel() {

    fun markToDoAsCompleted(todo: Todo) {
        TodoRepository.markTodoAsDone(todo)
    }

    fun reOpenTodo(todo: Todo){
        TodoRepository.reOpen(todo)
    }

    fun notify(todo: Todo){
        // TODO: write code for notifying
    }

}
