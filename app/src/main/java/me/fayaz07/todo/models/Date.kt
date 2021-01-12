package me.fayaz07.todo.models

import me.fayaz07.todo.utils.Helpers.Companion.getDay
import me.fayaz07.todo.utils.Helpers.Companion.getMonth
import java.util.*

data class TDate(var year: Int, var month: Int, val day: Int, val millis: Long)

fun fromMillis(millis: Long): TDate {
    calendar.timeInMillis = millis
    return TDate(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.DAY_OF_MONTH),
        millis
    )
}

fun TDate.isBefore(other: TDate): Boolean {
    return this.millis < other.millis
}

fun TDate.isAfter(other: TDate): Boolean {
    return this.millis > other.millis
}

fun TDate.difference(other: TDate): TDate {
    return TDate(
        this.year - other.year,
        this.month - other.month,
        this.day - other.day,
        0 // cause we don't use it
    )
}

fun TDate.isToday(today: TDate): Boolean {
    if (this.year == today.year && this.month == today.month && this.day == today.day) return true
    return false
}

fun TDate.whenItsCompleted(): String {
    return "${getDay(this.day)} ${getMonth(this.month)} ${this.year}"
}

