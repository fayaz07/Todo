package me.fayaz07.todo.utils

class Helpers{
    companion object {
        fun getMonth(selectedMonth: Int): String {
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

        fun getDay(selectedDay: Int): String {

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
}
