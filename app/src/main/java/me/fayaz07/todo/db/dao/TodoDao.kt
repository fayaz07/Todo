package me.fayaz07.todo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import me.fayaz07.todo.models.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): LiveData<List<Todo>>

    @Insert
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Query("DELETE FROM todo WHERE id=:id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM todo WHERE id=:id")
    fun getToDo(id: String): LiveData<Todo>
}
