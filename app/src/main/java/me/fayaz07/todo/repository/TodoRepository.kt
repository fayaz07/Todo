package me.fayaz07.todo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.fayaz07.todo.db.AppDatabase
import me.fayaz07.todo.models.Todo
import me.fayaz07.todo.models.TodoStatus
import me.fayaz07.todo.db.dao.TodoDao
import me.fayaz07.todo.models.isLagging

object TodoRepository {

    private var todoData: MutableList<Todo> = mutableListOf()

    private val mutableTodoList = MutableLiveData<List<Todo>>()
    val todoListLiveData: LiveData<List<Todo>> get() = mutableTodoList

    // database
    private lateinit var db: AppDatabase
    private lateinit var dao: TodoDao

    fun initDatabase(context: Context) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "todo_db"
        ).build()

        dao = db.todoDao()
        clearAndPull()
    }

    private fun clearAndPull() {
        clear()
        // exec the task using coroutines
        // if you do that on main thread, that will throw the following error
        // Caused by: java.lang.IllegalStateException: Cannot access database on the main
        //  thread since it may potentially lock the UI for a long period of time.
        CoroutineScope(IO).launch {
            val list = dao.getAll()
            todoData.addAll(0, list)
        }
        notifyChanges()
    }


    fun addTodo(title: String, description: String, dueOn: Long) {
        val id = todoData.size
        val newTask = Todo(id = id, title = title, description = description, dueOn = dueOn)
        if (newTask.isLagging()) {
            newTask.status = TodoStatus.Lagging
        } else {
            newTask.status = TodoStatus.Pending
        }
        todoData.add(newTask)

        // store in local database
        CoroutineScope(IO).launch {
            dao.insert(todo = newTask)
        }
        notifyChanges()
    }

    fun reOpen(todo: Todo) {
        todoData[todo.id].status = TodoStatus.Pending
        todoData[todo.id].completedOn = 0

        // update in local database
        CoroutineScope(IO).launch {
            dao.update(todoData[todo.id])
        }
        notifyChanges()
    }

    fun markTodoAsDone(todo: Todo) {
        val updatedTodo = markAsCompleted(todo)
        todoData[todo.id] = updatedTodo

        // update in local database
        CoroutineScope(IO).launch {
            dao.update(todoData[todo.id])
        }
        notifyChanges()
    }

    private fun markAsCompleted(todo: Todo): Todo {
        todo.completedOn = System.currentTimeMillis()
        todo.status = TodoStatus.Completed
        return todo
    }

    fun removeTodo(todo: Todo) {
        todoData.remove(todo)

        // update in local database
        CoroutineScope(IO).launch {
            dao.delete(todoData[todo.id])
        }
        notifyChanges()
    }

    fun clear() {
        todoData.clear()
//        CoroutineScope(IO).launch {
//
//        }
        notifyChanges()
    }

    private fun notifyChanges() {
        mutableTodoList.value = todoData
    }

}
