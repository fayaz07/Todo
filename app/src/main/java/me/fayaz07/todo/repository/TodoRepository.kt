package me.fayaz07.todo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.fayaz07.todo.models.TodoTask
import me.fayaz07.todo.models.TodoTaskStatus

object TodoRepository {

    private var todoData: MutableList<TodoTask> = mutableListOf()

    private val mutableTodoList = MutableLiveData<List<TodoTask>>()
    val todoListLiveData: LiveData<List<TodoTask>> get() = mutableTodoList

    fun addTodo(todo: TodoTask) {
        todoData.add(todo)
        notifyChanges()
    }

    fun markItemAsDone(todo: TodoTask, checked: Boolean) {
        val index: Int = todoData.indexOf(todo)
        if (checked)
            changeTodoTaskStatus(index, TodoTaskStatus.Completed)
        else
            changeTodoTaskStatus(index, TodoTaskStatus.Pending)
    }

    private fun changeTodoTaskStatus(index: Int, status: TodoTaskStatus) {
        todoData[index].status = status
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
