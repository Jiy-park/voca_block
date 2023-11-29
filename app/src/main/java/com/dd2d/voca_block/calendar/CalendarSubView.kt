package com.dd2d.voca_block.calendar

import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.common_ui.TT
import com.dd2d.voca_block.Values
import java.time.LocalDate

@Composable
fun BarOfYearMonth(
    year: Int,
    month: Int,
    day: Int,
    modifier: Modifier = Modifier,
    onClickPrev: (res: LocalDate)->Unit,
    onClickNext: (res: LocalDate)->Unit,
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        IconButton(
            onClick = { onClickPrev(LocalDate.of(year, month, day).minusMonths(1)) }
        ) { Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "preview month") }
        TT(text = "${Values.Common.Month.values()[month]}, $year")
        IconButton(
            onClick = { onClickNext(LocalDate.of(year, month, day).plusMonths(1)) }
        ) { Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "next month") }
    }
}

@Composable
fun BarOfWeek(modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White)
    ) {
        Values.Common.Day.values().forEachIndexed { i, day->
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .weight(1F)
            ){
                TT(
                    text = day.name,
                    color = if(i == 0) Color.Red else Color.Black
                )
            }
        }
    }
}

@Composable
fun BoxOfMonth(
    modifier: Modifier = Modifier,
    calendar: List<List<String>>,
    date: LocalDate,
    ripple: Indication,
    onClickDay: (year: Int, month: Int, day: Int) -> Unit
){
    calendar.forEach{ week ->
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            week.forEachIndexed { i, day ->
                BoxOfDay(
                    date = date,
                    day = day,
                    ripple = ripple,
                    isRedDay = i == 0,
                    modifier = modifier
                        .weight(1F)
                ){ year: Int, month: Int, day: Int ->
                    onClickDay(year, month, day)
                }
            }
        }
    }
}

@Composable
fun BoxOfDay(
    modifier: Modifier = Modifier,
    date: LocalDate,
    day: String,
    ripple: Indication,
    isRedDay: Boolean,
    onClickDay: (year: Int, month: Int, day: Int)->Unit
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(3F / 2F)
            .padding(3.dp)
            .drawBehind {
                if (date == LocalDate.now() && date.dayOfMonth.toString() == day) {
                    drawRoundRect(
                        color = Color.Gray,
                        style = Stroke(width = 2F),
                        cornerRadius = CornerRadius(x = 50F, y = 50F)
                    )
                }
            }
            .clickable(
                enabled = day != "-",
                interactionSource = MutableInteractionSource(),
                indication = ripple,
            ) {
                onClickDay(date.year, date.monthValue, day.toInt())
            }
    ){
        TT(
            text = if(day == "-") "" else day,
            color = if(isRedDay) Color.Red else Color.Black,
        )
    }
}