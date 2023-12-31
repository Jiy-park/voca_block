package com.dd2d.voca_block.view.word_book_view.setting_view.setting_view_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.common.TT
import com.dd2d.voca_block.struct.WordBookAutoOption

@Composable
fun WordBookSettingViewItem_AutoOption(
    modifier: Modifier = Modifier,
    autoOption: WordBookAutoOption,
    onChangeAutoOption: (option: WordBookAutoOption) -> Unit,
){
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
            TT(text = "자동 넘기기")
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
        ){
            TT(text = if(autoOption.autoScroll) "ON" else "OFF")
            Switch(
                checked = autoOption.autoScroll,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Color.Black,
                    uncheckedTrackColor = Color.LightGray,
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.White
                ),
                onCheckedChange = {
                    onChangeAutoOption(autoOption.copy(autoScroll = it))
                },
            )
        }
    }
}