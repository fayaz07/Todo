package me.fayaz07.todo.db.dao

import androidx.room.*
import me.fayaz07.todo.models.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): List<Todo>

    @Query("SELECT * FROM todo WHERE id IN (:todoIds)")
    fun loadAllByIds(todoIds: IntArray): List<Todo>

    @Query("SELECT * FROM todo WHERE title LIKE :title LIMIT 10")
    fun findByTitle(title: String): Todo

    @Insert
    fun insertAll(vararg todos: Todo)

    @Insert
    fun insert(todo: Todo)

    @Update
    fun update(todo: Todo)

    @Delete
    fun delete(todo: Todo)
}
