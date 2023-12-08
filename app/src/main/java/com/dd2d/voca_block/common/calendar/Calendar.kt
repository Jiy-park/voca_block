package com.dd2d.voca_block.common.calendar

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
import com.dd2d.voca_block.common.calendar.Calendar.Month.Temp
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

object Calendar {
    /** @property[Temp] 각 ordinal 을 월에 맞추기 위해 있음. 사용X*/
    enum class Month(val toKor: String) {
        Temp("Do Not Use"), Jan("1월"), Feb("2월"),
        Mar("3월"), Apr("4월"), May("5월"),
        Jun("6월"), Jul("7월"), Aug("8월"), Sep("9월"),
        Oct("10월"), Nov("11월"), Dec("12월")
    }

    enum class Day(val toKor: String, val toIndex: Int) {
        Sun("일요일", 0), Mon("월요일", 1), Tue("화요일", 2),
        Wed("수요일", 3), Thu("목요일", 4), Fri("금요일", 5), Sat("토요일", 6),
    }
}