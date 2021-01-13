package me.fayaz07.todo.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import me.fayaz07.todo.R
import me.fayaz07.todo.adapters.SwipeToDeleteCallback
import me.fayaz07.todo.adapters.TodoItemAdapter
import me.fayaz07.todo.databinding.ActivityHomeBinding
import me.fayaz07.todo.models.Todo
import me.fayaz07.todo.repository.TodoRepository
import me.fayaz07.todo.ui.add_todo.AddTodoActivity
import me.fayaz07.todo.ui.todo_detailed.TodoDetailedActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter:TodoItemAdapter
    private var oldList : List<Todo> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Your Todo list"

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.setContext(this)

        binding.todoListRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        adapter = TodoItemAdapter(showTodoDetailed)

        homeViewModel.todoListLiveData.observe(this, { list ->
            adapter.submitList(oldList, list)
            oldList = ArrayList(list)
            handleNoTasksView()
//            Toast.makeText(this, "length changed: ${list.size}", Toast.LENGTH_LONG).show()
        })

        binding.todoListRecyclerView.adapter = adapter
        binding.fabAddTodoButton.setOnClickListener { addTodo() }

        enableSwipeToDeleteAndUndo()
    }

    private val showTodoDetailed: (Todo) -> Unit = {
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

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: SwipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
//                val item: String = adapter.getData().get(position)
//                adapter.removeItem(position)

                TodoRepository.removeAtPosition(position)

                val snackbar = Snackbar
                    .make(
                        binding.coordinatorLayout,
                        "Item was removed from the list.",
                        Snackbar.LENGTH_LONG
                    )
                snackbar.setAction("UNDO") {
//                    adapter.restoreItem(item, position)
                    TodoRepository.restoreLastDeletedTodo()
                    binding.todoListRecyclerView.scrollToPosition(position)
                }
                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.show()
            }
        }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(binding.todoListRecyclerView)
    }

}
