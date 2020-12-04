package me.fayaz07.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.fayaz07.todo.R
import me.fayaz07.todo.databinding.NewTodoBottomSheetBinding


class AddTodoViewModel : ViewModel() {

    private val mutableSelectedItem = MutableLiveData<List<String>>()
    val todoItems: LiveData<List<String>> get() = mutableSelectedItem
    var todoList: MutableList<String> = mutableListOf()


    fun addTodo(todo: String) {
        todoList.add(todo)
        mutableSelectedItem.value = todoList
    }
}

class MyBottomSheetDialogFragment(val todoViewModel: AddTodoViewModel) :
        BottomSheetDialogFragment() {

    private lateinit var binding: NewTodoBottomSheetBinding


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.new_todo_bottom_sheet, container, false)

        // initialize dataBinding
        binding = DataBindingUtil.bind(v)!!

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.addTodoButton.setOnClickListener {
            val text: String = binding.todoTaskEditText.text.toString()
//            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT)
            todoViewModel.addTodo(text)
            dismiss()
        }
    }
}