package com.dd2d.voca_block.common_ui.label_slider

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.R
import com.dd2d.voca_block.common_ui.TT

/**
 * @param[labelSize] 라벨이 표시될 영역의 사이즈.
 * @param[thumbSize] 슬라이더의 thumb 사이즈.
 * @param[spaceOfLabelThumb] 라벨과 thumb 사이의 거리
 * @param[label] label 을 그리기 위한 람다. 람다에서 주어진 labelSize == [labelSize]*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelSlider(
    modifier: Modifier = Modifier,
    value: Float,
    visible: Boolean = true,
    valueRange:  ClosedFloatingPointRange<Float>,
    onValueChange: (Float)->Unit,
    enable: Boolean = true,
    onValueChangeFinished: (()->Unit)? = null,
    sliderColor: SliderColors = SliderDefaults.colors(),
    label: @Composable (labelSize1: DpSize)->Unit = { LabelSliderDefaultLabel(size = it, label = value.toInt().toString()) },
    labelSize: DpSize = DpSize(width = 60.dp, height = 50.dp),
    thumbSize: DpSize = DpSize(width = 15.dp, 15.dp),
    spaceOfLabelThumb: Dp = 15.dp,
){
    val innerModifier = Modifier
    Slider(
        value = value,
        onValueChange = { onValueChange(it) },
        onValueChangeFinished = { onValueChangeFinished?.let { it() } },
        valueRange = valueRange,
        enabled = enable,
        track = { SliderDefaults.Track(sliderPositions = it, colors = sliderColor) },
        thumb = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = innerModifier.offset(x = 0.dp, y = -labelSize.height/2 - spaceOfLabelThumb/2)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = innerModifier.size(labelSize)
                ){
                    label(labelSize)
                }
                Spacer(modifier = innerModifier.height(spaceOfLabelThumb))
                SliderDefaults.Thumb(
                    interactionSource = MutableInteractionSource(),
                    colors = sliderColor,
                    thumbSize = thumbSize,
                )
            }
        },
        modifier = modifier.wrapContentHeight().alpha(if(visible) 1F else 0F)
    )
}

@Composable
fun LabelSliderDefaultLabel(
    modifier: Modifier = Modifier,
    size: DpSize,
    label: String
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
    ){
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_bubble_chart),
            contentDescription = "",
            modifier = modifier
                .size(size)
        )
        TT(text = label)
    }
}