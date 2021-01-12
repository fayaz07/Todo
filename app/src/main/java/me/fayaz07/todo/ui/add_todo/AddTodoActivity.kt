package me.fayaz07.todo.ui.add_todo

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import me.fayaz07.todo.R
import me.fayaz07.todo.databinding.ActivityAddTodoBinding


class AddTodoActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var addTodoViewModel: AddTodoViewModel
    private lateinit var binding: ActivityAddTodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_todo)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        addTodoViewModel = ViewModelProvider(this).get(
            AddTodoViewModel::class.java
        )

        binding.pickDate.setOnClickListener {
            addTodoViewModel.showDatePicker(this, this)
        }

        binding.addTodoButton.setOnClickListener {
            checkFormAndAddNewTodo()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        addTodoViewModel.onDateSet(year, month, dayOfMonth)
        binding.whenItIsScheduled.text = addTodoViewModel.getWhenTodoIsScheduled()
    }


    private fun checkFormAndAddNewTodo() {
        val title: String = binding.titleField.text.toString()
        val description: String = binding.descField.text.toString()

        if (title.length < 3) {
            binding.titleField.error = "Please enter a valid title"
            return
        }

        if (description.length < 3) {
            binding.descField.error = "Please enter a valid description"
            return
        }
        addTodoViewModel.addNewTodo(title, description)
        finish()
    }
}
