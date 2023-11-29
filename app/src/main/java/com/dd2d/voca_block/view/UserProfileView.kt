package com.dd2d.voca_block.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dd2d.voca_block.calendar.Calendar
import com.dd2d.voca_block.common_ui.CircleProgress
import com.dd2d.voca_block.struct.Word

@Composable
//@Preview(showSystemUi = true)
fun UserProfileView(
    modifier: Modifier = Modifier,
    wordList: List<Word>,
    onClickDay: (year: Int, month: Int, day: Int)->Unit
){
    val memorize = wordList.filter { it.memorized }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxWidth().weight(1F)
        ){
            CircleProgress(current = memorize.size , total = wordList.size, modifier = modifier.fillMaxWidth(0.7F))
        }
        Box(modifier = modifier.fillMaxWidth().weight(1F)){
            Calendar(modifier = modifier.wrapContentSize()){ year, month, day ->
                onClickDay(year, month, day)
            }
        }

    }
}