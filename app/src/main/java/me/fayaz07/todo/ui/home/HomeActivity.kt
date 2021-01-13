package me.fayaz07.todo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import me.fayaz07.todo.R
import me.fayaz07.todo.ToDoApp
import me.fayaz07.todo.databinding.ActivityHomeBinding
import me.fayaz07.todo.models.Todo
import me.fayaz07.todo.ui.ViewModelFactory
import me.fayaz07.todo.ui.adapters.TodoItemAdapter
import me.fayaz07.todo.ui.add_todo.AddTodoActivity
import me.fayaz07.todo.ui.todo_detailed.TodoDetailedActivity
import me.fayaz07.todo.utils.Constants

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: TodoItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Your Todo list"

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        homeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(application as ToDoApp)
        ).get(HomeViewModel::class.java)

        adapter = TodoItemAdapter(
            onToDoClicked,
            onToDoChecked
        )

        binding.todoListRecyclerView.adapter = adapter

        homeViewModel.todoListLiveData.observe(this, { list ->
            adapter.submitList(list)
            handleNoTasksView(list)
        })

        binding.fabAddTodoButton.setOnClickListener { addTodo() }

        // enableSwipeToDeleteAndUndo()
    }

    private val onToDoClicked: (Todo) -> Unit = {
        val intent = Intent(this, TodoDetailedActivity::class.java)
        intent.putExtra(Constants.INTENT_CONSTANT_TODO_ID, it.id)
        startActivity(intent)
    }

    private val onToDoChecked: (Todo) -> Unit = {
        homeViewModel.markTodoDone(it)
    }

    private fun addTodo() {
        startActivity(Intent(this, AddTodoActivity::class.java))
    }

    private fun handleNoTasksView(list: List<Todo>) {
        if (list.isNotEmpty()) {
            // hide noTodoTasksHereView
            binding.noTodoTasksHereView.visibility = View.GONE
        } else {
            // show
            binding.noTodoTasksHereView.visibility = View.VISIBLE
        }
    }

//    private fun enableSwipeToDeleteAndUndo() {
//        val swipeToDeleteCallback: SwipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
//                val position = viewHolder.adapterPosition
////                val item: String = adapter.getData().get(position)
////                adapter.removeItem(position)
//
//                TodoRepository.removeToDo(position)
//
//                val snackbar = Snackbar
//                    .make(
//                        binding.coordinatorLayout,
//                        "Item was removed from the list.",
//                        Snackbar.LENGTH_LONG
//                    )
//                snackbar.setAction("UNDO") {
////                    adapter.restoreItem(item, position)
//                    TodoRepository.restoreLastDeletedTodo()
//                    binding.todoListRecyclerView.scrollToPosition(position)
//                }
//                snackbar.setActionTextColor(Color.YELLOW)
//                snackbar.show()
//            }
//        }
//        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
//        itemTouchHelper.attachToRecyclerView(binding.todoListRecyclerView)
//    }

}
