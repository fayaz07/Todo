package me.fayaz07.todo.ui.todo_detailed

import androidx.lifecycle.ViewModel
import me.fayaz07.todo.models.TodoTask
import me.fayaz07.todo.repository.TodoRepository

class TodoDetailedViewModel : ViewModel() {

    fun markToDoAsCompleted(todoTask: TodoTask) {
        TodoRepository.markTodoAsDone(todoTask)
    }

    fun reOpenTodo(todoTask: TodoTask){
        TodoRepository.reOpen(todoTask)
    }

    fun notify(todoTask: TodoTask){
        // TODO: write code for notifying
    }

}
