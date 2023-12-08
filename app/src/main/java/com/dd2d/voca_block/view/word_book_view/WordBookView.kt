package com.dd2d.voca_block.view.word_book_view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.dd2d.voca_block.TTS
import com.dd2d.voca_block.common.WordType
import com.dd2d.voca_block.common.FontSize
import com.dd2d.voca_block.common.LocalFontSize
import com.dd2d.voca_block.struct.WordBookAutoOption
import com.dd2d.voca_block.view.word_book_view.card.CardMode
import com.dd2d.voca_block.view.word_book_view.category_selector.WordBookCategorySelectorView
import com.dd2d.voca_block.view.word_book_view.setting_view.WordBookSettingView
import com.dd2d.voca_block.view_model.CategoryViewModel
import com.dd2d.voca_block.view_model.WordCategoryViewModel
import com.dd2d.voca_block.view_model.WordsViewModel

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
    val tts by remember { mutableStateOf(TTS.getInstance(context, wordType)) }

    var wordMode by remember { mutableStateOf<WordMode>(WordMode.Card) }
    var autoOption by remember { mutableStateOf(WordBookAutoOption(autoScroll = true)) }

    var isOpenCategorySelector by remember { mutableStateOf(false) }
    var isOpenSetting by remember { mutableStateOf(true) }


    var fontSize by remember { mutableStateOf(FontSize.Default) }
    CompositionLocalProvider(
        LocalFontSize provides fontSize
    ) {
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
                        onChangeMode = { mode -> wordMode = mode },
                        onChangeMemorize = { word, isMemorized ->  wordsViewModel.updateWordMemorized(word, isMemorized) },
                        onUpdateWordCategory = { wordId, categoryId, onCategory -> wordCategoryViewModel.updateWordCategory(wordId, categoryId, onCategory) }
                    )
                }
                WordMode.List -> {

                }
            }


            IconButton(onClick = { isOpenSetting = !isOpenSetting }, modifier = modifier.align(Alignment.TopEnd)) {
                Icon(imageVector = if(isOpenSetting) Icons.Default.Close else Icons.Default.Settings, contentDescription = "")
            }
            WordBookSettingView(
                isOpenSetting = isOpenSetting,
                wordMode = wordMode,
                fontSize = fontSize,
                autoOption = autoOption,
                onChangeWordMode = { wordMode = it },
                onChangeFontSize = { fontSize = it },
                onChangeAutoOption = { autoOption = it },
                modifier = modifier.align(Alignment.BottomCenter)
            )

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
}