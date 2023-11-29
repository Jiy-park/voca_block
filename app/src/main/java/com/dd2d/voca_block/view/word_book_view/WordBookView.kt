package com.dd2d.voca_block.view.word_book_view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dd2d.voca_block.Values.WordMode
import com.dd2d.voca_block.view.word_book_view.card.CardMode
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

    var wordMode by remember { mutableStateOf<WordMode>(WordMode.Card) }

    when(wordMode){
        WordMode.Card -> {
            CardMode(
                modifier = modifier,
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
}