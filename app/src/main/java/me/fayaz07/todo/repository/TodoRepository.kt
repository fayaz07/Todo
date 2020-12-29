package me.fayaz07.todo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.fayaz07.todo.models.TodoTask
import me.fayaz07.todo.models.TodoTaskStatus
import me.fayaz07.todo.models.calendar
import me.fayaz07.todo.models.isLagging
import java.text.SimpleDateFormat
import java.util.*

object TodoRepository {

    private var todoData: MutableList<TodoTask> = mutableListOf()

    private val mutableTodoList = MutableLiveData<List<TodoTask>>()
    val todoListLiveData: LiveData<List<TodoTask>> get() = mutableTodoList

    fun addTodo(title: String, description: String, dueOn: Date) {
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

    fun getTodoById(id: Int): TodoTask {
        return todoData[id]
    }


    fun markTodoAsDone(todo: TodoTask) {
        val updatedTodo = markAsCompleted(todo)
        todoData[todo.id] = updatedTodo
        notifyChanges()
    }

    private fun markAsCompleted(todo: TodoTask): TodoTask {
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        val string = "$currentDay-$currentMonth-$currentYear"
        todo.completedOn = SimpleDateFormat("dd-MM-yyyy").parse(string)
        todo.status = TodoTaskStatus.Completed
        return todo
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
