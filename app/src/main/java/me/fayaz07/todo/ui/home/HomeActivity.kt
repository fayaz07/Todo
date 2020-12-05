package me.fayaz07.todo.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import me.fayaz07.todo.R
import me.fayaz07.todo.adapters.TodoItemAdapter
import me.fayaz07.todo.databinding.ActivityHomeBinding
import me.fayaz07.todo.ui.add_todo.AddTodoActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Your Todo list"

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.todoListRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        homeViewModel.todoListLiveData.observe(this, { list ->
//            binding.todoList.adapter = ArrayAdapter(
//                this,
//                android.R.layout.simple_list_item_1,
//                list
//            )

            val adapter: TodoItemAdapter = TodoItemAdapter(list, homeViewModel.handleTodoTask)
            binding.todoListRecyclerView.adapter = adapter
            handleNoTasksView()

            Toast.makeText(this, "length changed: ${list.size}", Toast.LENGTH_LONG).show()
        })


        binding.fabAddTodoButton.setOnClickListener { addTodo() }

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
