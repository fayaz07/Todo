package me.fayaz07.todo.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import me.fayaz07.todo.R
import me.fayaz07.todo.models.TodoTask
import me.fayaz07.todo.models.TodoTaskStatus
import me.fayaz07.todo.models.isLagging
import me.fayaz07.todo.repository.TodoRepository
import kotlin.properties.Delegates

class TodoItemAdapter(private var todoList: List<TodoTask>, val onClick: (todo: TodoTask) -> Unit) :
    RecyclerView.Adapter<TodoItemAdapter.TodoItemView>() {

    private var greenColor by Delegates.notNull<Int>()
    private var orangeColor by Delegates.notNull<Int>()
    private var redColor by Delegates.notNull<Int>()

    init {
        todoList.sortedBy { i -> i.dueOn.time }
        todoList.sortedBy { i -> i.status == TodoTaskStatus.Completed }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)

        // init colors
        greenColor = ContextCompat.getColor(parent.context, R.color.todo_done)
        redColor = ContextCompat.getColor(parent.context, R.color.todo_due)
        orangeColor = ContextCompat.getColor(parent.context, R.color.todo_pending)
        return TodoItemView(view)
    }

    override fun onBindViewHolder(holder: TodoItemView, position: Int) {
        val todoTask = todoList[position]

        holder.textView.text = todoTask.title

        when (todoTask.status) {
            TodoTaskStatus.Completed -> {

                holder.parent.strokeColor = greenColor

                holder.checkBox.isChecked = true
                holder.checkBox.isEnabled = false

                holder.textView.setTextColor(greenColor)
                holder.textView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            TodoTaskStatus.Pending -> {

                holder.parent.strokeColor = orangeColor

                holder.checkBox.isChecked = false

                holder.textView.setTextColor(orangeColor)
                holder.textView.paintFlags = Paint.LINEAR_TEXT_FLAG
            }
            TodoTaskStatus.Lagging -> {

                holder.parent.strokeColor = redColor

                holder.checkBox.isChecked = false

                holder.textView.setTextColor(redColor)
                holder.textView.paintFlags = Paint.LINEAR_TEXT_FLAG
            }
        }

//        todoTask.getDueIn()

        if (todoTask.isLagging()){
            holder.parent.strokeColor = redColor

            holder.checkBox.isChecked = false

            holder.textView.setTextColor(redColor)
            holder.textView.paintFlags = Paint.LINEAR_TEXT_FLAG
        }

        holder.checkBox.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                TodoRepository.markItemAsDone(todoTask)
            }
        }

        holder.parent.setOnClickListener {
            onClick(todoTask)
        }
    }



    override fun getItemCount(): Int {
        return todoList.size
    }

    class TodoItemView(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var checkBox: CheckBox = itemView.findViewById(R.id.checkBoxTodoItem)
        var textView: TextView = itemView.findViewById(R.id.titleViewTodoItem)
        var parent: MaterialCardView =
            itemView.findViewById(R.id.parentTodoItem)

    }
}
