package me.fayaz07.todo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.fayaz07.todo.models.TodoTask
import me.fayaz07.todo.models.TodoTaskStatus
import me.fayaz07.todo.models.isLagging

object TodoRepository {

    private var todoData: MutableList<TodoTask> = mutableListOf()

    private val mutableTodoList = MutableLiveData<List<TodoTask>>()
    val todoListLiveData: LiveData<List<TodoTask>> get() = mutableTodoList

    fun addTodo(title: String, description: String, dueOn: Long) {
        val id = todoData.size
        val newTask = TodoTask(id = id, title = title, description = description, dueOn = dueOn)
        if(newTask.isLagging()){
            newTask.status = TodoTaskStatus.Lagging
        }else{
            newTask.status = TodoTaskStatus.Pending
        }
        todoData.add(newTask)
        notifyChanges()
    }

    fun reOpen(todo: TodoTask){
        todoData[todo.id].status = TodoTaskStatus.Pending
        todoData[todo.id].completedOn = 0
        notifyChanges()
    }

    fun markTodoAsDone(todo: TodoTask) {
        val updatedTodo = markAsCompleted(todo)
        todoData[todo.id] = updatedTodo
        notifyChanges()
    }

    private fun markAsCompleted(todo: TodoTask): TodoTask {
        todo.completedOn = System.currentTimeMillis()
        todo.status = TodoTaskStatus.Completed
        return todo
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
