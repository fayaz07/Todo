package me.fayaz07.todo.adapters

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import me.fayaz07.todo.R
import me.fayaz07.todo.models.TodoTask
import me.fayaz07.todo.models.TodoTaskStatus
import me.fayaz07.todo.repository.TodoRepository

class TodoItemAdapter(private var todoList: List<TodoTask>, val onClick: (todo: TodoTask) -> Unit) :
    RecyclerView.Adapter<TodoItemAdapter.TodoItemView>() {

    init {
        todoList.sortedBy { i -> i.dueOn.time }
        todoList.sortedBy { i -> i.status == TodoTaskStatus.Completed }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)

        return TodoItemView(view)
    }

    override fun onBindViewHolder(holder: TodoItemView, position: Int) {
        val todoTask = todoList[position]

        if (todoTask.status == TodoTaskStatus.Completed) {
            holder.checkBox.isChecked = true
            holder.checkBox
                .highlightColor = Color.parseColor("#FF03DAC5")
            holder.textView.setTextColor(Color.parseColor("#FF03DAC5"))
            holder.textView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.checkBox.isChecked = false
        }
        holder.textView.text = todoTask.title

        holder.checkBox.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            TodoRepository.markItemAsDone(todoTask, isChecked)
        }

        holder.parent.setOnClickListener {
            onItemTap(todoTask)
        }
    }

    private fun onItemTap(todo: TodoTask) {
        onClick(todo)
    }


    override fun getItemCount(): Int {
        return todoList.size
    }

    class TodoItemView(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var checkBox: CheckBox = itemView.findViewById<CheckBox>(R.id.checkBoxTodoItem)
        var textView: TextView = itemView.findViewById<TextView>(R.id.titleViewTodoItem)
        var parent: MaterialCardView =
            itemView.findViewById<MaterialCardView>(R.id.parentTodoItem)

    }
}
