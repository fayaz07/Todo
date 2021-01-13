package me.fayaz07.todo.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import me.fayaz07.todo.R
import me.fayaz07.todo.models.*
import me.fayaz07.todo.repository.TodoRepository
import kotlin.properties.Delegates

class TodoItemAdapter(val onClick: (todo: Todo) -> Unit) :
    RecyclerView.Adapter<TodoItemAdapter.TodoItemView>() {

    private var todoList: List<Todo> = emptyList<Todo>()
    private var greenColor by Delegates.notNull<Int>()
    private var orangeColor by Delegates.notNull<Int>()
    private var redColor by Delegates.notNull<Int>()

    init {
        todoList.sortedBy { i -> i.dueOn }
        todoList.sortedBy { i -> i.status == TodoStatus.Completed }
    }

    fun submitList(oldList: List<Todo>, newList: List<Todo>) {

        print("Oldlist: ")
        println(ArrayList(todoList))
        print("Newlist: ")
        println(ArrayList(newList))

        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            TodoDiffUtilCallBack(
                ArrayList(todoList), ArrayList(newList)
            )
        )
        todoList = ArrayList(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class TodoDiffUtilCallBack(var oldList: List<Todo>, var newList: List<Todo>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].equals(newList[newItemPosition])
        }

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

        holder.title.text = todoTask.title

        holder.status.text = todoTask.getCompletionStatus()

        when (todoTask.status) {
            TodoStatus.Completed -> {
                holder.parent.setCardBackgroundColor(greenColor)

                holder.checkBox.isChecked = true
                holder.checkBox.isEnabled = false

//                holder.title.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            TodoStatus.Pending -> {
                holder.parent.setCardBackgroundColor(orangeColor)

                holder.checkBox.isChecked = false

//                holder.title.paintFlags = Paint.LINEAR_TEXT_FLAG
            }
            TodoStatus.Lagging -> {
                holder.parent.setCardBackgroundColor(redColor)

                holder.checkBox.isChecked = false

//                holder.title.paintFlags = Paint.LINEAR_TEXT_FLAG
            }
        }

        if (fromMillis(todoTask.dueOn).isToday(fromMillis(System.currentTimeMillis())) && todoTask.status != TodoStatus.Completed) {
            holder.parent.setCardBackgroundColor(orangeColor)

            holder.checkBox.isChecked = false
        }

        holder.checkBox.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                TodoRepository.markTodoAsDone(todoTask)
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
        var title: TextView = itemView.findViewById(R.id.titleViewTodoItem)
        var status: TextView = itemView.findViewById(R.id.statusTodoItem)

        var parent: MaterialCardView =
            itemView.findViewById(R.id.parentTodoItem)

    }
}
