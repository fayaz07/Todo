package me.fayaz07.todo.ui.add_todo

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import me.fayaz07.todo.repository.TodoRepository
import me.fayaz07.todo.utils.Helpers.Companion.getDay
import me.fayaz07.todo.utils.Helpers.Companion.getMonth
import java.util.*

class AddTodoViewModel : ViewModel() {

    private var currentDay: Int = 0
    private var currentMonth: Int = 0
    private var currentYear: Int = 0
    private var selectedDay: Int = 0
    private var selectedMonth: Int = 0
    private var selectedYear: Int = 0

    init {
        initCurrentDate()
        initSelectedDate()
    }


    fun addNewTodo(title: String, description: String) {
        val c = Calendar.getInstance()
        c.set(selectedYear, selectedMonth, selectedDay)
        TodoRepository.addTodo(
            title = title,
            description = description,
            dueOn = c.timeInMillis
        )
    }

    fun showDatePicker(context: Context, listener: DatePickerDialog.OnDateSetListener) {
        initCurrentDate()
        val datePickerDialog =
            DatePickerDialog(context, listener, currentYear, currentMonth, currentDay)
        datePickerDialog.show()
    }

    private fun initCurrentDate() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        currentMonth = calendar.get(Calendar.MONTH)
        currentYear = calendar.get(Calendar.YEAR)
    }

    private fun initSelectedDate() {
        selectedDay = currentDay
        selectedMonth = currentMonth
        selectedYear = currentYear
    }

    fun getWhenTodoIsScheduled(): String {
        initCurrentDate()
        if (currentYear == selectedYear && currentMonth == selectedMonth) {
            if (currentDay == selectedDay) {
                return "Today"
            }
            if (currentDay == selectedDay - 1) {
                return "Tomorrow"

            }
            if (currentDay == selectedDay + 1) {
                return "Yesterday"
            }
        }
        return "${getDay(selectedDay)} ${getMonth(selectedMonth)}"
    }

    fun onDateSet(year: Int, month: Int, dayOfMonth: Int) {
        selectedDay = dayOfMonth
        selectedYear = year
        selectedMonth = month
    }

}
