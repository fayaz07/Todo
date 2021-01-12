package me.fayaz07.todo.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.fayaz07.todo.R
import me.fayaz07.todo.adapters.TodoItemAdapter
import me.fayaz07.todo.databinding.ActivityHomeBinding
import me.fayaz07.todo.models.TodoTask
import me.fayaz07.todo.ui.add_todo.AddTodoActivity
import me.fayaz07.todo.ui.todo_detailed.TodoDetailedActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Your Todo list"

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.setContext(this)

        binding.todoListRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        homeViewModel.todoListLiveData.observe(this, { list ->

            val adapter = TodoItemAdapter(list, showTodoDetailed)
            binding.todoListRecyclerView.adapter = adapter
            handleNoTasksView()
//            Toast.makeText(this, "length changed: ${list.size}", Toast.LENGTH_LONG).show()
        })
        binding.fabAddTodoButton.setOnClickListener { addTodo() }
    }

    private val showTodoDetailed: (TodoTask) -> Unit = {
        val intent = Intent(this, TodoDetailedActivity::class.java)
        intent.putExtra("todoId", it.id)
        startActivity(intent)
    }

    private fun addTodo() {
        startActivity(Intent(this, AddTodoActivity::class.java))
    }

    private fun handleNoTasksView() {
        if (homeViewModel.todoListLiveData.value?.size!! > 0) {
            // hide noTodoTasksHereView
            binding.noTodoTasksHereView.visibility = View.GONE
        } else {
            // show
            binding.noTodoTasksHereView.visibility = View.VISIBLE
        }
    }

}
