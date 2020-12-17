package me.fayaz07.todo.models

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

val calendar: Calendar = Calendar.getInstance()

data class TodoTask(
    val id: Int,
    val title: String,
    val description: String,
    val dueOn: Date,
    var status: TodoTaskStatus = TodoTaskStatus.Pending,
) {
    lateinit var completedOn: Date
}

enum class TodoTaskStatus { Pending, Completed, Lagging }

fun getTodayDateInstance(): Date {
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentYear = calendar.get(Calendar.YEAR)

    val string = "$currentDay-$currentMonth-$currentYear"
    return SimpleDateFormat("dd-MM-yyyy").parse(string)
}

fun TodoTask.isLagging(): Boolean {

    if (this.status == TodoTaskStatus.Completed) {
        return false
    }

    val today = getTodayDateInstance()

    if (this.dueOn.before(today)) {
        return true
    }

    return false
}

fun TodoTask.getDueIn(): String {
    val today = getTodayDateInstance()

//    val dueInLong: Long = this.dueOn.time - today.time
    val dueInYears: Int = this.dueOn.year - today.year
    val dueInMonths: Int = this.dueOn.month - today.month
    val dueInDays: Int = this.dueOn.day - today.day
    Log.d("DAY", "$dueInYears, $dueInMonths ,$dueInDays")
    var dueInString = "";
    return dueInString

}
