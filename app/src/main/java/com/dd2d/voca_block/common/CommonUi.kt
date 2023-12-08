package com.dd2d.voca_block.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.dd2d.voca_block.R
import com.dd2d.voca_block.struct.Word

@Composable
fun TT(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    fontSize: FontSize = FontSize.Default,
    color: Color = Color.Black,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLine: Int = 1,
){
    Text(
        text = text,
        fontSize = fontSize.size,
        color = color,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLine,
        modifier = modifier
    )
}

@Composable
fun MemorizeIcon(
    word: Word,
    onChangeMemorize: (word: Word, isMemorized: Boolean)->Unit,
    modifier: Modifier = Modifier
){
    if(word.memorized){
        IconButton(
            onClick = { onChangeMemorize(word, !word.memorized) },
            modifier = modifier
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_memorize), contentDescription = "memorize")
        }
    }
}

@Composable
fun BookmarkIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Icon(
            painterResource(id = R.drawable.ic_un_bookmark),
            contentDescription = "book mark icon"
        )
    }
}

@Composable
fun CircleProgress(
    current: Int,
    total: Int,
    trackColor: Color = Color.LightGray,
    progressColor: Color = Color.Black,
    modifier: Modifier = Modifier
){
    val animatedPercent = remember { Animatable(0F) }

    LaunchedEffect(current, total){
        animatedPercent.animateTo(
            targetValue = if(total != 0) current/total.toFloat() else { 0F },
            animationSpec = tween(durationMillis = 1000)
        )
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .drawBehind {
                val radius = size.width/3F
                val topLeft = Offset(center.x - radius, center.y - radius)
                val strokeWidth = radius/3F
                drawCircle(
                    color = trackColor,
                    center = center,
                    radius = radius,
                    style = Stroke(width = strokeWidth)
                )
                drawArc(
                    color = progressColor,
                    startAngle = 270F,
                    sweepAngle = animatedPercent.value * 360F,
                    topLeft = topLeft,
                    size = Size(radius * 2, radius * 2),
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round
                    )
                )
            }
            .aspectRatio(1F / 1F)
    ){
        TT(text = "%.2f".format(animatedPercent.value*100) + "%", fontSize = FontSize.Largest)
        TT(text = "$current/$total", fontSize = FontSize.Small)
    }
}

@Composable
fun SS(
    text: String = "슬라이더",
    range: ClosedFloatingPointRange<Float> ,
    value: Float,
    onValueChange: (changed: Float)->Unit,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ){
        TT(text = "$text:\n" + "%.2f".format(value), maxLine = 2, modifier = modifier.weight(0.3F))
        Slider(
            value = value,
            onValueChange = { onValueChange(it) },
            valueRange = range,
            modifier = modifier
                .wrapContentHeight()
                .weight(1F)
        )
    }
}