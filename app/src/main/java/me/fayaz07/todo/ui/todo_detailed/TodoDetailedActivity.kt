package me.fayaz07.todo.ui.todo_detailed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import me.fayaz07.todo.R
import me.fayaz07.todo.databinding.ActivityTodoDetailedBinding
import me.fayaz07.todo.models.TodoTask
import me.fayaz07.todo.models.TodoTaskStatus
import me.fayaz07.todo.repository.TodoRepository

class TodoDetailedActivity : AppCompatActivity() {

    lateinit var todoTask: TodoTask
    lateinit var binding: ActivityTodoDetailedBinding
    lateinit var viewModel: TodoDetailedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo_detailed)

        supportActionBar?.title = "Todo detailed"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id: Int = intent.getIntExtra("todoId", 0)
        todoTask = TodoRepository.getTodoById(id)
        Log.d("todo-task", todoTask.title)

        viewModel = ViewModelProvider(this).get(TodoDetailedViewModel::class.java)

        // set values
        binding.todoTitle.text = todoTask.title
        binding.todoDescription.text = todoTask.description
        binding.todoStatus.text = todoTask.status.name

        when (todoTask.status) {
            TodoTaskStatus.Pending -> binding.todoStatus.setTextColor(
                ContextCompat.getColor(this, R.color.todo_pending)
            )
            TodoTaskStatus.Completed -> binding.todoStatus.setTextColor(
                ContextCompat.getColor(this, R.color.todo_done)
            )
            TodoTaskStatus.Lagging -> binding.todoStatus.setTextColor(
                ContextCompat.getColor(
                    this, R.color.todo_due
                )
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
