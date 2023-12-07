package com.dd2d.voca_block.view.word_book_view.card

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.dd2d.voca_block.Values.Common.FontSize
import com.dd2d.voca_block.Values.Common.LocalFontSize
import com.dd2d.voca_block.Values.WordMode.Card
import com.dd2d.voca_block.Values.WordMode.Card.SwipeTo
import com.dd2d.voca_block.common_ui.BookmarkIcon
import com.dd2d.voca_block.common_ui.MemorizeIcon
import com.dd2d.voca_block.common_ui.TT
import com.dd2d.voca_block.struct.Category
import com.dd2d.voca_block.struct.Word
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * @param[wordCategory] 해당 단어의 카테고리를 리스트 형태로 받아옴. 카테고리에  속해있을 경우 [true]로 표시
 * @param[onEndSelect] 카테고리 선택 창 종료 시 호출. 1.TopEnd 버튼으로 종료 시 [null] 반환. 2. 선택 완료 시 카테고리 아이디와 체크 결과를 Pair로 묶어 리스트 형태로 반환*/
@Composable
fun CategorySelector(
    modifier: Modifier = Modifier,
    wordCategory: List<Pair<Category, Boolean>>,
    onEndSelect: (afterSelectList: List<Pair<Int, Boolean>>?)->Unit,
){
    val onCategoryList = wordCategory.map { it.second }.toMutableStateList()
    val categoryIdList = wordCategory.map { it.first.id }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
            .width(Card.CardMaxWidth.dp)
            .height(Card.CardMaxHeight.dp)
            .background(color = Color.White)
            .padding(5.dp)
    ){
        IconButton(onClick = { onEndSelect(null) },) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "close category selector")
        }
        LazyColumn(
            state = rememberLazyListState(),
            modifier = modifier
                .fillMaxWidth()
                .weight(1F)
        ) {
            itemsIndexed(onCategoryList){ index, onCategory->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ) {
                    TT(text = wordCategory[index].first.name)
                    Checkbox(
                        checked = onCategory,
                        onCheckedChange = { onCategoryList[index] = it }
                    )
                }
            }
        }
        TextButton(
            onClick = {
                onEndSelect(categoryIdList.zip(onCategoryList))
            }
        ) {
            TT(text = "선택 완료")
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ForegroundView(
    modifier: Modifier = Modifier,
    word: Word,
    pagerState: PagerState,
    page: Int,
    swipePos: Float,
    totalPage: Int,
    openCategorySelector: () -> Unit,
    onChangeMemorize: (word: Word, isMemorized: Boolean) -> Unit,
    onSwipeFinished: (swipe: SwipeTo)->Unit,
    onSwipe: (delta: Float) -> Unit
){
    val animatedColor by animateColorAsState(targetValue = if (swipePos < -150F) Color.Green else Color.White, label = "")
    val currentPage = pagerState.currentPage
    val pageOffset = ((currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
    val height = lerp(
        start = Card.CardMinHeight.dp,
        stop = Card.CardMaxHeight.dp,
        fraction = 1f - pageOffset.coerceIn(0f, 1f)
    )
    Box(
        modifier = modifier
            .offset(x = 0.dp, y = swipePos.coerceIn(Card.VerticalSwipeRange).dp)
            .width(Card.CardMaxWidth.dp)
            .height(height)
            .background(color = animatedColor)
            .shadow(elevation = 2.5.dp,)
            .draggable(
                onDragStopped = {
                    when {
                        swipePos < Card.UpperSwipeTrigger -> onSwipeFinished(SwipeTo.Up)
                        swipePos > Card.LowerSwipeTrigger -> onSwipeFinished(SwipeTo.Down)
                    }
                },
                state = rememberDraggableState { delta -> onSwipe(delta) },
                orientation = Orientation.Vertical
            )
    ){
        MemorizeIcon(word = word, onChangeMemorize = { word, isMemorized-> onChangeMemorize(word, isMemorized) }, modifier = modifier.align(Alignment.TopStart))
        BookmarkIcon(onClick = { openCategorySelector() }, modifier = modifier.align(Alignment.TopEnd))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .align(Alignment.Center)
                .padding(15.dp)
        ) {
            TT(
                text = word.word,
                fontSize = LocalFontSize.current,
            )
            Spacer(modifier = modifier.height(3.dp))
            TT(
                text = word.mean,
                fontSize = LocalFontSize.current,
            )
        }
        WordOrderView(order = "${page + 1}/$totalPage", modifier = modifier.align(Alignment.BottomEnd))
    }
}


@Composable
fun BackgroundView(
    modifier: Modifier = Modifier,
    onClick: suspend ()->Unit
){
    var onRotate by remember { mutableStateOf(false) }
    val rotateAngle by animateFloatAsState(
        targetValue = if(onRotate) 360F else 0F,
        label = "",
        finishedListener = { onRotate = false }
    )
    val scope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ){
        IconButton(
            onClick = {
                onRotate = true
                scope.launch { onClick() }
            },
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                tint = Color.Black,
                contentDescription = "",
                modifier = modifier
                    .size(50.dp)
                    .graphicsLayer { rotationZ = rotateAngle }
            )
        }
    }

}

@Composable
private fun WordOrderView(
    order: String,
    modifier: Modifier = Modifier
){
    TT(text = order, fontSize = FontSize.Smallest, color = Color.LightGray, modifier = modifier)
}