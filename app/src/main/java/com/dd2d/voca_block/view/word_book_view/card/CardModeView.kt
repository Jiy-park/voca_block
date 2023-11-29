package com.dd2d.voca_block.view.word_book_view.card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.Values
import com.dd2d.voca_block.Values.Common.FontSize
import com.dd2d.voca_block.Values.WordMode.Card.SwipeTo
import com.dd2d.voca_block.struct.Category
import com.dd2d.voca_block.struct.Word
import com.dd2d.voca_block.struct.WordCategory
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardModeView(
    list: List<Word>,
    fontSize: FontSize,
    categoryList: List<Category>,
    wordCategoryList: List<WordCategory>,
    onUpdateWordCategory: (wordId: Int, categoryId: Int, onCategory: Boolean)->Unit,
    onChangeMemorize: (word: Word, isMemorized: Boolean)->Unit,
    modifier: Modifier = Modifier,
){
    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()
    val size = list.size
    HorizontalPager(
        pageCount = size,
        state = pagerState,
        pageSpacing = 30.dp,
        contentPadding = PaddingValues(horizontal = 50.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(Values.WordMode.Card.CardMaxHeight.dp)
    ) { page->
        val word = list[page]
        var swipePos by remember { mutableStateOf(0F) }
        var isOpenCategorySelector by remember { mutableStateOf(false) }
    
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
        ){
            BackgroundView(modifier.matchParentSize()){
                animate(
                    initialValue = swipePos,
                    targetValue = 0F,
                ){ value, _ -> swipePos = value }
            }
            ForegroundView(
                word = word,
                pagerState = pagerState,
                page = page,
                swipePos = swipePos,
                fontSize = fontSize,
                totalPage = list.size,
                openCategorySelector = { isOpenCategorySelector = !isOpenCategorySelector },
                onChangeMemorize = { w,b-> onChangeMemorize(w, b) },
                onSwipeFinished = { swipe->
                    when(swipe){
                        SwipeTo.Up -> {
                            onChangeMemorize(word, true)
                            if(pagerState.canScrollForward){
                                scope.launch { pagerState.animateScrollToPage(page+1) }
                            }
                        }
                        SwipeTo.None -> {  }
                        SwipeTo.Down -> { }
                    }
                }
            ){ delta-> swipePos += delta * 1.3F }
            AnimatedVisibility(
                visible = isOpenCategorySelector ,
                enter = slideInVertically(
                    animationSpec = tween(500)
                ){ -it*2 },
                exit = slideOutVertically(
                    animationSpec = tween(500)
                ){ -it*2 }
            ) {
                CategorySelector(
                    wordCategory = getWordCategory(word, categoryList, wordCategoryList)
                ){
                    it?.let { afterSelect->
                        afterSelect.forEach { (categoryId, onCategory)->
                            onUpdateWordCategory(word.id, categoryId, onCategory)
                        }
                    }
                    isOpenCategorySelector = !isOpenCategorySelector
                }
            }
        }
    }
}

@Composable
fun getWordCategory(
    word: Word,
    categoryList: List<Category>,
    wordCategoryList: List<WordCategory>,
): List<Pair<Category, Boolean>>{
    val wordId = word.id
    val wordCategory = wordCategoryList
        .asSequence()
        .filter { it.wordId == wordId }
        .map { it.categoryId }

    return categoryList.map { category->
        category to (category.id in wordCategory)
    }
}
