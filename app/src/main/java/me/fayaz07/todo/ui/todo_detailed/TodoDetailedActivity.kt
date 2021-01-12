package me.fayaz07.todo.ui.todo_detailed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import me.fayaz07.todo.R
import me.fayaz07.todo.databinding.ActivityTodoDetailedBinding
import me.fayaz07.todo.models.*
import me.fayaz07.todo.repository.TodoRepository

class TodoDetailedActivity : AppCompatActivity() {

    private lateinit var todoTask: TodoTask
    private lateinit var binding: ActivityTodoDetailedBinding
    private lateinit var viewModel: TodoDetailedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo_detailed)

        supportActionBar?.title = "Todo detailed"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id: Int = intent.getIntExtra("todoId", 0)

        viewModel = ViewModelProvider(this).get(TodoDetailedViewModel::class.java)

        TodoRepository.todoListLiveData.observe(this, { list ->
            todoTask = list[id]
            Log.d("todo-task", todoTask.title)

            // set values
            updateUI()
        })

        // register listeners
        binding.completeTodo.setOnClickListener {
            markItemAsCompleted()
        }

        binding.notifyTodo.setOnClickListener {
            notifyMe()
        }

        binding.openTodo.setOnClickListener {
            reOpen()
        }
    }

    private fun markItemAsCompleted() {
        viewModel.markToDoAsCompleted(todoTask)
    }

    private fun notifyMe() {
        viewModel.notify(todoTask)
    }

    private fun reOpen() {
        viewModel.reOpenTodo(todoTask)
    }

    private fun updateUI() {
        binding.todoTitle.text = todoTask.title
        binding.todoDescription.text = todoTask.description
        binding.todoStatus.text = todoTask.status.name
        binding.todoScheduledOn.text = fromMillis(todoTask.dueOn).whenItsCompleted()

        todoTask.getCompletionStatus()

        when (todoTask.status) {
            TodoTaskStatus.Pending -> {
                binding.todoStatus.setTextColor(
                    ContextCompat.getColor(this, R.color.todo_pending)
                )
                binding.todoCompletedOn.text = "Not yet completed"

                binding.openTodo.visibility = View.GONE
                binding.completeTodo.visibility = View.VISIBLE
                binding.notifyTodo.visibility = View.VISIBLE
            }
            TodoTaskStatus.Completed -> {
                binding.todoStatus.setTextColor(
                    ContextCompat.getColor(this, R.color.todo_done)
                )
                binding.todoCompletedOn.text = fromMillis(todoTask.completedOn).whenItsCompleted()

                binding.openTodo.visibility = View.VISIBLE
                binding.completeTodo.visibility = View.GONE
                binding.notifyTodo.visibility = View.GONE
            }
            TodoTaskStatus.Lagging -> {
                binding.todoStatus.setTextColor(
                    ContextCompat.getColor(
                        this, R.color.todo_due
                    )
                )
                binding.todoCompletedOn.text = "Not yet completed"

                binding.openTodo.visibility = View.GONE
                binding.completeTodo.visibility = View.VISIBLE
                binding.notifyTodo.visibility = View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
