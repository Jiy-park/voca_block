package com.dd2d.voca_block.view.word_book_view

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.Values.CategorySelectorValue
import com.dd2d.voca_block.Values.Common.DetailType
import com.dd2d.voca_block.Values.Common.FontSize
import com.dd2d.voca_block.Values.Common.WordType
import com.dd2d.voca_block.Values.WordMode
import com.dd2d.voca_block.common_ui.TT
import com.dd2d.voca_block.struct.Category
import com.dd2d.voca_block.view.word_book_view.card.CardMode
import com.dd2d.voca_block.view_model.CategoryViewModel
import com.dd2d.voca_block.view_model.WordCategoryViewModel
import com.dd2d.voca_block.view_model.WordsViewModel
import java.util.Locale

class TTS{
    /** TTS, 단어를 주면 해당 단어를 읽어줌.
     * @param[meanTTS] 단어의 뜻을 읽어줌.
     * @param[wordTTS] 단어를 읽어줌.*/
    data class Instance(
        val wordTTS: TextToSpeech,
        val meanTTS: TextToSpeech,
    ){
        fun speakWord(word: String, endSpeak: ()->Unit){
            wordTTS.speak(word, TextToSpeech.QUEUE_FLUSH, null, null)
            endSpeak()
        }

        fun speakMean(mean: String, endSpeak: ()->Unit){
            meanTTS.speak(mean, TextToSpeech.QUEUE_FLUSH, null, null)
            endSpeak()
        }
    }

    companion object {
        private var wordInstance: TextToSpeech? = null
        private var meanInstance: TextToSpeech? = null

        fun getInstance(context: Context, wordType: WordType): Instance{
            if(wordInstance != null && meanInstance != null) return Instance(wordInstance!!, meanInstance!!)
            else{
                val (typeWord, typeMean) = wordType.type
                wordInstance = TextToSpeech(context){
                    wordInstance?.language = when(typeWord){
                        DetailType.En -> { Locale.US }
                        DetailType.Kr -> { Locale.KOREA }
                    }
                }

                meanInstance = TextToSpeech(context){
                    meanInstance?.language = when(typeMean){
                        DetailType.En -> { Locale.US }
                        DetailType.Kr -> { Locale.KOREA }
                    }
                }

                return Instance(wordInstance!!, meanInstance!!)
            }
        }
    }
}

@Composable
fun WordBookView(
    modifier: Modifier = Modifier,
    wordsViewModel: WordsViewModel,
    categoryViewModel: CategoryViewModel,
    wordCategoryViewModel: WordCategoryViewModel
){
    val wordList by wordsViewModel.wordList.collectAsState(initial = emptyList())
    val categoryList by categoryViewModel.category.collectAsState(initial = emptyList())
    val wordCategoryList by wordCategoryViewModel.selectedCategory.collectAsState(initial = emptyList())

    val wordType by remember { mutableStateOf(WordType.EnKr) }
    val context = LocalContext.current
    var wordMode by remember { mutableStateOf<WordMode>(WordMode.Card) }
    val tts by remember { mutableStateOf(TTS.getInstance(context, wordType)) }

    var isOpenCategorySelector by remember { mutableStateOf(false) }
    var isOpenSetting by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ){
        when(wordMode){
            WordMode.Card -> {
                CardMode(
                    modifier = modifier,
                    tts = tts,
                    wordList = wordList,
                    categoryList = categoryList,
                    wordCategoryList = wordCategoryList,
                    onEndCreateCategory = { categoryName, description -> categoryViewModel.addNewCategory(categoryName, description) },
                    onChangeCategory = { categoryId-> wordsViewModel.selectByCategoryId(categoryId) },
                    onChangeMode = { mode -> wordMode = mode },
                    onChangeMemorize = { word, isMemorized ->  wordsViewModel.updateWordMemorized(word, isMemorized) },
                    onUpdateWordCategory = { wordId, categoryId, onCategory -> wordCategoryViewModel.updateWordCategory(wordId, categoryId, onCategory) }
                )
            }
            WordMode.List -> {

            }
        }

        WordBookSettingView(
            isOpenSetting = isOpenSetting,
            modifier = modifier
                .align(Alignment.TopEnd),
        ) { isOpenSetting = it }

        IconButton(onClick = { isOpenCategorySelector = true }) {
            Icon(imageVector = Icons.Default.List, contentDescription = "")
        }
        WordBookCategorySelectorView(
            isOpenCategorySelector = isOpenCategorySelector,
            categoryList = categoryList,
            onDismissRequest = { isOpenCategorySelector = false },
            onClickCategory = { categoryId-> wordsViewModel.selectByCategoryId(categoryId) },
            onEditCategory = { after-> categoryViewModel.updateCategory(after) },
            modifier = modifier
        )
    }
}

@Composable
fun WordBookCategorySelectorView(
    modifier: Modifier = Modifier,
    categoryList: List<Category>,
    isOpenCategorySelector: Boolean,
    onDismissRequest: ()->Unit,
    onClickCategory: (categoryId: Int)->Unit,
    onEditCategory: (after: Category)->Unit,
){
    AnimatedVisibility(
        visible = isOpenCategorySelector,
        enter = slideInHorizontally() { -1000 } + fadeIn(),
        exit = slideOutHorizontally() { -2000 } + fadeOut(),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            IconButton(onClick = { onDismissRequest() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "")
            }
            LazyColumn(
                state = rememberLazyListState(),
                contentPadding = PaddingValues(10.dp),
            ){
                CategorySelectorValue.values().forEach { value->
                    item{
                        CategorySelectorViewItem(
                            item = Category(id = value.id, name = value.toName, description = value.description),
                            onClick = { categoryId->
                                onClickCategory(categoryId)
                                onDismissRequest()
                            }
                        )
                    }
                }
                items(items = categoryList){ category->
                    var onEditMode by remember { mutableStateOf(false) }
                    CategorySelectorViewItem(
                        item = category,
                        onClick = { categoryId->
                            onClickCategory(categoryId)
                            onDismissRequest()
                        },
                        use = true,
                        onEditMode = onEditMode,
                        onChangeEditMode = { onEditMode != onEditMode }
                    )

                }
            }
        }
    }
}

/** 시용자가 만든 카테고리를 표시. 해당 카테고리는 수정 가능
 * @see[CategorySelectorViewItem] */
@Composable
fun CategorySelectorViewItem(
    modifier: Modifier = Modifier,
    item: Category,
    onClick: (categoryId: Int)->Unit,
    use: Boolean = false,
    onEditMode: Boolean,
    onChangeEditMode: (()->Unit)? = null,
){
    TextButton(onClick = { onClick(item.id) }) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()
            ){
                Column(
                    modifier = modifier.wrapContentWidth()
                ) {
                    TT(text = item.name, textAlign = TextAlign.Left, fontSize = FontSize.Default)
                    TT(text = item.description, textAlign = TextAlign.Left, fontSize = FontSize.Smallest)
                }
                if(use){
                    IconButton(onClick = { onChangeEditMode!!() }) {
                        Icon(imageVector = Icons.Default.Edit, tint = Color.Black,contentDescription = "")
                    }
                }
            }
            AnimatedVisibility(
                visible = onEditMode,
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(color = Color.Red)
            ) {
                TT(text = "asdasd")
            }
        }
    }
}

/**- 기본적으로 존재하는 카테고리를 표시. 해당 카테고리는 수정 불가능.
 * @see[CategorySelectorValue]
 * @see[CategorySelectorViewItem]*/
@Composable
fun CategorySelectorViewItem(
    modifier: Modifier = Modifier,
    item: Category,
    onClick: (categoryId: Int)->Unit,
){
    TextButton(onClick = { onClick(item.id) }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ){
            Column(
                modifier = modifier.wrapContentWidth()
            ) {
                TT(text = item.name, textAlign = TextAlign.Left, fontSize = FontSize.Default)
                TT(text = item.description, textAlign = TextAlign.Left, fontSize = FontSize.Smallest)
            }
        }
    }
}

@Composable
fun WordBookSettingView(
    modifier: Modifier = Modifier,
    isOpenSetting: Boolean,
    onClickSettingIcon: (res: Boolean)->Unit,
){
    Box(
        modifier = modifier
            .wrapContentWidth()
            .height(50.dp)
    ){
        IconToggleButton(
            checked = isOpenSetting,
            onCheckedChange = { onClickSettingIcon(it) },
        ) {
            Icon(
                imageVector = if(isOpenSetting) Icons.Default.Close else Icons.Default.Settings,
                contentDescription = ""
            )
        }
    }
}

