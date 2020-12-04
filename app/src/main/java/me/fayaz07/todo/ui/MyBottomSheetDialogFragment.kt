package me.fayaz07.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import me.fayaz07.todo.R
import me.fayaz07.todo.databinding.NewTodoBottomSheetBinding

class MyBottomSheetDialogFragment(private val todoViewModel: AddTodoViewModel) :
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
            todoViewModel.addTodo(text)
            dismiss()
        }
    }
}