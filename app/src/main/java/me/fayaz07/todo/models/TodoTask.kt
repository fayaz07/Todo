package me.fayaz07.todo.models

import java.util.*

val calendar: Calendar = Calendar.getInstance()

data class TodoTask(
    val id: Int,
    val title: String,
    val description: String,
    val dueOn: Long,
    var status: TodoTaskStatus = TodoTaskStatus.Pending,
    var completedOn: Long = 0
)

enum class TodoTaskStatus { Pending, Completed, Lagging }

fun getTodayDateInstance(): TDate {
    return fromMillis(System.currentTimeMillis())
}

fun TodoTask.isLagging(): Boolean {

    if (this.status == TodoTaskStatus.Completed) {
        return false
    }

    val today = getTodayDateInstance()

    // due time is greater than today's time
    if (this.dueOn < today.millis) {
        return true
    }

    return false
}

fun TodoTask.getCompletionStatus(): String {


    val today = getTodayDateInstance()

    val isScheduledOnToday = fromMillis(this.dueOn).isToday(today)


    if (this.status == TodoTaskStatus.Completed) {

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


