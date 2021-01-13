package me.fayaz07.todo.repository

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import me.fayaz07.todo.db.dao.TodoDao
import me.fayaz07.todo.models.Todo

class TodoRepository(
    private val dao: TodoDao
) {

    private lateinit var lastDeletedTodo: Todo

    suspend fun addTodo(todo: Todo) = withContext(IO) {
        dao.insert(todo)
    }


    suspend fun update(todo: Todo) = withContext(IO){
            dao.update(todo)
    }

    suspend fun markTodoAsDone(todo: Todo) = withContext(IO) {
        dao.update(todo)
    }

    suspend fun removeToDo(id: String) = withContext(IO) {
        dao.delete(id)
    }

//    fun restoreLastDeletedTodo() {
//        todoData.add(lastDeletedTodo.id, lastDeletedTodo)
////        notifyChanges()
//    }

    fun getList() = dao.getAll()

    fun getToDo(id: String) = dao.getToDo(id)

}
