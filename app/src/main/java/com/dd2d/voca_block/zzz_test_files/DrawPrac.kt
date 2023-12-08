package com.dd2d.voca_block.zzz_test_files

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.common.SS
import com.dd2d.voca_block.util.log
import kotlin.random.Random

@Composable
@Preview(showSystemUi = true)
fun DrawPractice(
    modifier: Modifier = Modifier,
){
//    Column(
//        modifier = modifier,
//    ) {
//        CircleProgress(
//            current = wordList.value.count { it.memorized },
//            total = wordList.value.size
//        )
////        Calendar { year, month, day ->
////            year.log("year")
////            month.log("month")
////            day.log("day")
////        }
//    }

}


@Composable
fun ColorSlider(
    color: Color,
    onColorChange: (res: Color)->Unit,
    modifier: Modifier = Modifier
){
    color.log("color")
    val colors = listOf(color.alpha, color.red, color.green, color.blue)
    val names = listOf("alpha", "red", "green", "blue")
    Column(
        modifier = modifier
    ) {
//        colors.forEachIndexed { i, v ->
//            SS(
//                text = names[i],
//                range = 0F..1F,
//                value = v,
//                onValueChange = { new->
//                    val newColor = when(i){
//                        0-> Color(new, colors[1], colors[2], colors[3])
//                        1-> Color(colors[0], new, colors[2], colors[3])
//                        2-> Color(colors[0], colors[1], new, colors[3])
//                        3-> Color(colors[0], colors[1], colors[2], new)
//                        else ->{ Color.Black }
//                    }.log("new Color")
//                    onColorChange(newColor)
//                }
//            )
//        }

        var a by remember { mutableStateOf(color.alpha) }
        var r by remember { mutableStateOf(color.red) }
        var g by remember { mutableStateOf(color.green) }
        var b by remember { mutableStateOf(color.blue) }

        SS(text = "alpha", range = 0F..1F, value = a,
            onValueChange = {
                it.log("a ")
                a = it
                onColorChange(Color(r, g, b, a))
            },
        )
        SS(text = "R", range = 0F..1F, value = r,
            onValueChange = {
                it.log("r ")
                r = it
                onColorChange(Color(r, g, b, a))
            }
        )
        SS(text = "G", range = 0F..1F, value = g,
            onValueChange = {
                it.log("g ")
                g = it
                onColorChange(Color(r, g, b, a))
            },
        )
        SS(text = "B", range = 0F..1F, value = b,
            onValueChange = {
                it.log("b ")
                b = it
                onColorChange(Color(r, g, b, a))
            },
        )
    }
}

@Composable
fun PieChart(
    data: List<Any>,
    radius: Float = 50F,
    modifier: Modifier = Modifier
){
    val categories = data.distinct()
    val colors = generateRandomColorList(categories.size)
    Spacer(
        modifier = modifier
            .drawBehind {
                var startAngle = 270F
                var sweepAngle = 0F

                categories.forEachIndexed { index, category->
                    val ratio = data.count { it == category }/data.size.toFloat()
                    sweepAngle = ratio * 360F
                    drawArc(
                        color = colors[index],
                        topLeft = Offset(center.x-radius.dp.toPx()/2, center.y-radius.dp.toPx()/2),
                        size = Size(radius.dp.toPx(), radius.dp.toPx()),
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true
                    )
                    startAngle += ratio * 360F
                }
            }
    )
}


//@Composable
//fun ProgressCircle(
//    percent: Float,
//    radius: Float = 150F,
//    strokeWidth: Float = radius/3,
//    trackColor: Color = Color.LightGray,
//    progressColor: Color = Color.Black,
//    modifier: Modifier = Modifier
//){
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = modifier
//            .drawBehind {
//                val topLeft = Offset(center.x - radius, center.y - radius)
//                drawCircle(
//                    color = trackColor,
//                    center = center,
//                    radius = radius,
//                    style = Stroke(width = strokeWidth)
//                )
//                drawArc(
//                    color = progressColor,
//                    startAngle = 270F,
//                    sweepAngle = percent * 360F,
//                    topLeft = topLeft,
//                    size = Size(radius * 2, radius * 2),
//                    useCenter = false,
//                    style = Stroke(
//                        width = strokeWidth,
//                        cap = StrokeCap.Round
//                    )
//                )
//            }
//            .aspectRatio(1F / 1F)
//    ){
//        TT(text = "%.2f".format(percent*100) + "%" )
//    }
//}

fun generateList(totalElements: Int): List<String> {
    val resultList = mutableListOf<String>()

    var countA = 0
    var countB = 0
    var countC = 0

    repeat(totalElements) {
        when ((1..3).random()) {
            1 -> {
                resultList.add("A")
                countA++
            }
            2 -> {
                resultList.add("B")
                countB++
            }
            3 -> {
                resultList.add("C")
                countC++
            }
        }
    }

    println("Count of A: $countA, B: $countB, C: $countC")
    return resultList
}

fun generateRandomColorList(totalColors: Int): List<Color> {
    val colorList = mutableListOf<Color>()

    repeat(totalColors) {
        val randomColor = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        colorList.add(randomColor)
    }

    return colorList
}
