package me.fayaz07.todo.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import me.fayaz07.todo.R
import me.fayaz07.todo.databinding.ActivityHomeBinding
import me.fayaz07.todo.ui.MyBottomSheetDialogFragment
import me.fayaz07.todo.ui.add_todo.AddTodoViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Your Todo list"

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.todoListLiveData.observe(this, { list ->
            binding.todoList.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                list
            )
        })
    }
    
}
