package com.dd2d.voca_block.view.word_book_view.setting_view_item

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.R
import com.dd2d.voca_block.Values.Common.FontSize
import com.dd2d.voca_block.common_ui.TT

@Composable
fun WordBookSettingViewItem_FontSize(
    modifier: Modifier = Modifier,
    fontSize: FontSize,
    onChangeFontSize: (size: FontSize)->Unit
){
    val indexOfCurrentFontSize = FontSize.values().indexOf(fontSize)
    /** [FontSize.Unspecified] (index == 0) 제외한 폰트 사이즈 중 제일 작은 값*/
    val smallestFontSize = FontSize.values().drop(1).first()
    val largestFontSize = FontSize.values().last()

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
            TT(text = "글자 크기 변경")
        }

        Divider(
            modifier
                .width(1.dp)
                .fillMaxHeight(0.5F), color = Color.Black)

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .weight(1F)
        ) {
            IconButton(
                onClick = { onChangeFontSize(FontSize.values()[indexOfCurrentFontSize-1]) },
                enabled = fontSize != smallestFontSize
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_minus), contentDescription = "")
            }
            TT(text = fontSize.toKor)
            IconButton(
                onClick = { onChangeFontSize(FontSize.values()[indexOfCurrentFontSize+1]) },
                enabled = fontSize != largestFontSize
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_plus), contentDescription = "")
            }
        }
    }
}