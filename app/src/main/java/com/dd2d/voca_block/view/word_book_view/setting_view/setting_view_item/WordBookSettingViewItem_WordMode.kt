package com.dd2d.voca_block.view.word_book_view.setting_view.setting_view_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.common.TT
import com.dd2d.voca_block.view.word_book_view.WordMode
import com.dd2d.voca_block.view.word_book_view.WordModeValues
import kotlin.math.abs

@Composable
fun WordBookSettingViewItem_WordMode(
    modifier: Modifier = Modifier,
    wordMode: WordMode,
    onChangeWordMode: (mode: WordMode)->Unit,
){
    val indexOfCurrentWordMode = WordModeValues.indexOf(wordMode)
    val modeCount = WordModeValues.size

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth(0.5F)
        ){
            TT(text = "보기 모드 변경")
        }

        Divider(modifier .width(1.dp).fillMaxHeight(0.5F), color = Color.Black)

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .weight(1F)
        ) {
            IconButton(
                onClick = {
                    val prev = abs(indexOfCurrentWordMode - 1) % modeCount
                    onChangeWordMode(WordModeValues[prev])
                }
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "")
            }
            TT(text = wordMode.toKor)
            IconButton(
                onClick = {
                    val next = (indexOfCurrentWordMode + 1) % modeCount
                    onChangeWordMode(WordModeValues[next])
                }
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
            }
        }
    }
}