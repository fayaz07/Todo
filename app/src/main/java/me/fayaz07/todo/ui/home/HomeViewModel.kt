package me.fayaz07.todo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.fayaz07.todo.models.TodoTask
import me.fayaz07.todo.repository.TodoRepository

class HomeViewModel : ViewModel() {

    val todoListLiveData: LiveData<List<TodoTask>> get() = TodoRepository.todoListLiveData
}
