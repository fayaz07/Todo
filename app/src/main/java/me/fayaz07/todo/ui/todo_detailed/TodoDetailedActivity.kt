package me.fayaz07.todo.ui.todo_detailed

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import me.fayaz07.todo.R
import me.fayaz07.todo.ToDoApp
import me.fayaz07.todo.databinding.ActivityTodoDetailedBinding
import me.fayaz07.todo.models.*
import me.fayaz07.todo.ui.ViewModelFactory
import me.fayaz07.todo.utils.Constants

class TodoDetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoDetailedBinding
    private lateinit var viewModel: TodoDetailedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo_detailed)

        supportActionBar?.title = "Todo detailed"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id: String = intent.getStringExtra(Constants.INTENT_CONSTANT_TODO_ID) ?: ""

        viewModel = ViewModelProvider(this, ViewModelFactory(application as ToDoApp)).get(
            TodoDetailedViewModel::class.java
        )

        viewModel.getToDo(id).observe(this) {
            it?.let { todo ->
                updateUI(todo)
                viewModel.todo = todo
            }
        }
        // register listeners
        binding.completeTodo.setOnClickListener {
            viewModel.markToDoAsCompleted()
        }

        binding.notifyTodo.setOnClickListener {

        }

        binding.openTodo.setOnClickListener {
            viewModel.updateToDo()
        }
    }


    private fun updateUI(todo: Todo) {
        binding.todoTitle.text = todo.title
        binding.todoDescription.text = todo.description
        binding.todoStatus.text = todo.status.name
        binding.todoScheduledOn.text = fromMillis(todo.dueOn).whenItsCompleted()

        todo.getCompletionStatus()

        when (todo.status) {
            TodoStatus.Pending -> {
                binding.todoStatus.setTextColor(
                    ContextCompat.getColor(this, R.color.todo_pending)
                )
                binding.todoCompletedOn.text = "Not yet completed"

                binding.openTodo.visibility = View.GONE
                binding.completeTodo.visibility = View.VISIBLE
                binding.notifyTodo.visibility = View.VISIBLE
            }
            TodoStatus.Completed -> {
                binding.todoStatus.setTextColor(
                    ContextCompat.getColor(this, R.color.todo_done)
                )
                binding.todoCompletedOn.text = fromMillis(todo.completedOn).whenItsCompleted()

                binding.openTodo.visibility = View.VISIBLE
                binding.completeTodo.visibility = View.GONE
                binding.notifyTodo.visibility = View.GONE
            }
            TodoStatus.Lagging -> {
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
}
