package me.fayaz07.todo.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

val calendar: Calendar = Calendar.getInstance()

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val dueOn: Long,
    var status: TodoStatus = TodoStatus.Pending,
    var completedOn: Long = 0
)

enum class TodoStatus { Pending, Completed, Lagging }

fun getTodayDateInstance(): TDate {
    return fromMillis(System.currentTimeMillis())
}

fun Todo.isLagging(): Boolean {

    if (this.status == TodoStatus.Completed) {
        return false
    }

    val today = getTodayDateInstance()

    if (fromMillis(this.dueOn).isToday(today)){
        return false
    }

    // due time is greater than today's time
    if (this.dueOn < today.millis) {
        return true
    }

    return false
}

fun Todo.getCompletionStatus(): String {


    val today = getTodayDateInstance()

    val isScheduledOnToday = fromMillis(this.dueOn).isToday(today)


    if (this.status == TodoStatus.Completed) {

        if (isScheduledOnToday)
            return "Completed Today"

        return "Completed on ${fromMillis(this.completedOn).whenItsCompleted()}"
    }

    if (isScheduledOnToday)
        return "To be done Today"


    val lagging = this.isLagging()

    var diffTDate: TDate = if (lagging) {
//        today.difference(fromMillis(this.dueOn))
        fromMillis(today.millis - this.dueOn)
    } else {
//        fromMillis(this.dueOn).difference(today)
        fromMillis(this.dueOn - today.millis)
    }

    diffTDate.year = diffTDate.year - 1970
    diffTDate.month = diffTDate.month - 1

    var dueInString = if (lagging) {
        "Lagging "
    } else {
        "Due in "
    }
    if (diffTDate.year == 1) {
        dueInString += "${diffTDate.year} year "
    } else if (diffTDate.year > 1) {
        dueInString += "${diffTDate.year} years "
    }

    if (diffTDate.month == 1) {
        dueInString += "${diffTDate.month} month "
    } else if (diffTDate.month > 1) {
        dueInString += "${diffTDate.month} months "
    }

    if (diffTDate.day == 1) {
        dueInString += "${diffTDate.day} day "
    } else if (diffTDate.day > 1) {
        dueInString += "${diffTDate.day} days "
    }

    return dueInString
}


