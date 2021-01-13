package me.fayaz07.todo.models

import android.util.Log
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
) {

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) {
            return false
        }

        other as Todo

//        Log.d("test", toString() + " " + other.toString() )

        if (id != other.id) {
            return false
        }
        if (title != other.title) {
            return false
        }
        if (description != other.description) {
            return false
        }
        if (dueOn != other.dueOn) {
            return false
        }
        if (status != other.status) {
            return false
        }
        if (completedOn != other.completedOn) {
            return false
        }
        return true
    }

    override fun toString(): String {
        return "Todo(id=$id, title='$title', description='$description', dueOn=$dueOn, status=$status, completedOn=$completedOn)"
    }


}

enum class TodoStatus { Pending, Completed, Lagging }

fun getTodayDateInstance(): TDate {
    return fromMillis(System.currentTimeMillis())
}

fun Todo.isLagging(): Boolean {

    if (this.status == TodoStatus.Completed) {
        return false
    }

    val today = getTodayDateInstance()

    if (fromMillis(this.dueOn).isToday(today)) {
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


