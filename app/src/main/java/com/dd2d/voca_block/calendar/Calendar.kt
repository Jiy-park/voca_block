package com.dd2d.voca_block.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    onClickDay: (year: Int, month: Int, day: Int) -> Unit,
){
    var date by remember { mutableStateOf(LocalDate.now()) }
    val year = date.year
    val month = date.monthValue
    val day = date.dayOfMonth

    val calendar = getCalendar(year, month, day)
    val ripple = rememberRipple(
        bounded = false,
        color = Color.Red,
        radius = 20.dp
    )
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(color = Color.LightGray)
    ) {
        // 월 년 바
        BarOfYearMonth(
            year = year,
            month = month,
            day = day,
            onClickPrev = { res-> date = res },
            onClickNext = { res-> date = res }
        )
        BarOfWeek()
        BoxOfMonth(
            calendar = calendar,
            date = date,
            ripple = ripple
        ){ y, m ,d->
            onClickDay(y, m ,d)
        }
    }
}