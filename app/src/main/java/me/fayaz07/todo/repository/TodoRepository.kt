package me.fayaz07.todo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.fayaz07.todo.models.TodoTask

object TodoRepository {

    private var todoData: MutableList<TodoTask> = mutableListOf()

    private val mutableTodoList = MutableLiveData<List<TodoTask>>()
    val todoListLiveData: LiveData<List<TodoTask>> get() = mutableTodoList

    fun addTodo(todo: TodoTask) {
        todoData.add(todo)
        notifyChanges()
    }

    fun removeTodo(todo: TodoTask) {
        todoData.remove(todo)
        notifyChanges()
    }

    fun clear() {
        todoData.clear()
        notifyChanges()
    }

    private fun notifyChanges() {
        mutableTodoList.value = todoData
    }

}
