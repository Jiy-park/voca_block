package com.dd2d.voca_block.view.word_book_view.setting_view.setting_view_item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.common.Common.DefaultAutoScrollDelayRange
import com.dd2d.voca_block.common.LabelSlider
import com.dd2d.voca_block.common.LabelSliderDefaultLabel
import com.dd2d.voca_block.common.TT
import com.dd2d.voca_block.struct.WordBookAutoOption

@Composable
@Preview(showSystemUi = true)
fun WordBookSettingViewItem_AutoScrollDelay(
    modifier: Modifier = Modifier,
    autoOption: WordBookAutoOption = WordBookAutoOption(true),
    onChangeAutoOption: (option: WordBookAutoOption) -> Unit = {  },
){
    var onDragging by remember { mutableStateOf(false) }
    val sliderColor = SliderDefaults.colors(
        thumbColor = Color.White,
        activeTrackColor = Color.Black,
        inactiveTickColor = Color.LightGray,
    )
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
            TT(text = "넘기기 속도")
        }

        Divider(
            modifier
                .width(1.dp)
                .fillMaxHeight(0.5F), color = Color.Black)

        LabelSlider(
            value = autoOption.autoScrollDelay.toFloat(),
            valueRange = DefaultAutoScrollDelayRange,
            onValueChange = {
                onChangeAutoOption(autoOption.copy(autoScrollDelay = it.toLong()))
                onDragging = true
            },
            onValueChangeFinished = { onDragging = false },
            visible = autoOption.autoScroll,
            sliderColor = sliderColor,
//            enable = !(autoOption.autoWordSpeak or autoOption.autoMeanSpeak),
            label = {
                AnimatedVisibility(visible = onDragging, enter = fadeIn(), exit = fadeOut()) {
                    val delayToSec = autoOption.autoScrollDelay.toFloat()/1000
                    LabelSliderDefaultLabel(size = it, label = String.format("%.1f 초", delayToSec))
                }
            },
            modifier = Modifier.weight(1F)
        )
    }
}