package com.dd2d.voca_block.zzz_test_files

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun PieChart(
    modifier: Modifier = Modifier,
    data: List<Any>,
    radius: Float = 50F,
){
    val categories = data.distinct()
    val colors = generateRandomColorList(categories.size)
    Spacer(
        modifier = modifier
            .drawBehind {
                var startAngle = 270F
                var sweepAngle: Float

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
