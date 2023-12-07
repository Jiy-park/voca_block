package com.dd2d.voca_block.common_ui.calendar

import java.time.LocalDate

fun getCalendar(year: Int, month: Int, day: Int): List<List<String>>{
    val calendar = MutableList(42){ "-" }
    val lastDayOfMonth = LocalDate.of(year, month, day).lengthOfMonth()
    val weekOfDay1 = LocalDate.of(year, month, 1).dayOfWeek.value % 7 // 일요일을 0, 월요일을 1....

    for(i in weekOfDay1 until weekOfDay1+lastDayOfMonth){
        calendar[i] = (i - weekOfDay1 + 1).toString()
    }
    return calendar.chunked(7)
}