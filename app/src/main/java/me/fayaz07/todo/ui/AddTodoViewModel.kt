package me.fayaz07.todo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddTodoViewModel : ViewModel() {

    private val mutableSelectedItem = MutableLiveData<List<String>>()
    val todoItems: LiveData<List<String>> get() = mutableSelectedItem
    private var todoList: MutableList<String> = mutableListOf()
    
    fun addTodo(todo: String) {
        todoList.add(todo)
        mutableSelectedItem.value = todoList
    }
}
