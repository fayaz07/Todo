package me.fayaz07.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import me.fayaz07.todo.R
import me.fayaz07.todo.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var todoViewModel: AddTodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Your Todo list"

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        todoViewModel = ViewModelProvider(this).get(AddTodoViewModel::class.java)

        todoViewModel.todoItems.observe(this, { list ->
            binding.todoList.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    list
            )
        })

    }

    private fun showAddToDoDialog() {
        MyBottomSheetDialogFragment(todoViewModel).apply {
            show(supportFragmentManager, tag)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_todo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addToDoContextMenuButton) {
            showAddToDoDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}