package me.fayaz07.todo.ui.add_todo

import android.app.DatePickerDialog
import android.content.Context
import androidx.lifecycle.ViewModel
import me.fayaz07.todo.models.TodoTask
import me.fayaz07.todo.repository.TodoRepository
import java.text.SimpleDateFormat
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
    }

    fun addNewTodo(title: String, description: String) {
        val string = "$selectedDay-$selectedMonth-$selectedYear"
        val date: Date = SimpleDateFormat("dd-MM-yyyy").parse(string)
        TodoRepository.addTodo(TodoTask(title = title, description = description, date))
    }

    fun showDatePicker(context: Context, listener: DatePickerDialog.OnDateSetListener) {
        initCurrentDate()
        val datePickerDialog =
            DatePickerDialog(context, listener, currentYear, currentMonth, currentDay)
        datePickerDialog.show()
    }

    private fun initCurrentDate() {
        val calendar: Calendar = Calendar.getInstance()
        currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        currentMonth = calendar.get(Calendar.MONTH)
        currentYear = calendar.get(Calendar.YEAR)
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
        return "${getDay()} ${getMonth()}"
    }

    fun onDateSet(year: Int, month: Int, dayOfMonth: Int) {
        selectedDay = dayOfMonth
        selectedYear = year
        selectedMonth = month
    }

    private fun getMonth(): String {
        when (selectedMonth) {
            0 -> return "January"
            1 -> return "February"
            2 -> return "March"
            3 -> return "April"
            4 -> return "May"
            5 -> return "June"
            6 -> return "July"
            7 -> return "August"
            8 -> return "September"
            9 -> return "October"
            10 -> return "November"
            11 -> return "December"
        }
        return " "
    }

    private fun getDay(): String {

        var endsWith = 0

        if (selectedDay >= 20) {
            endsWith = selectedDay - 20
        }
        if (selectedDay >= 30) {
            endsWith = selectedDay - 30
        }

        when (endsWith) {
            1 -> return "${selectedDay}st"
            2 -> return "${selectedDay}nd"
            3 -> return "${selectedDay}rd"
        }
        return "${selectedDay}th"
    }
}
