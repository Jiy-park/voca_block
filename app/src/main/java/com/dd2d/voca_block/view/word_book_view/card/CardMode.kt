package com.dd2d.voca_block.view.word_book_view.card
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dd2d.voca_block.TTS
import com.dd2d.voca_block.Values.TestMode
import com.dd2d.voca_block.Values.WordMode
import com.dd2d.voca_block.struct.Category
import com.dd2d.voca_block.struct.Word
import com.dd2d.voca_block.struct.WordBookAutoOption
import com.dd2d.voca_block.struct.WordCategory

/**
 * @param[tts] TTS, pair.first: word 읽음. pair.second: mean 읽음*/
@Composable
fun CardMode (
    modifier: Modifier = Modifier,
    tts: TTS.Instance,
    wordList: List<Word>,
    categoryList: List<Category>,
    wordCategoryList: List<WordCategory>,
//    onEndCreateCategory: (categoryName: String, description: String)->Unit,
//    onChangeCategory: (categoryId: Int)->Unit,
    onUpdateWordCategory: (wordId: Int, categoryId: Int, onCategory: Boolean)->Unit,
    onChangeMode: (mode: WordMode)->Unit,
    onChangeMemorize: (word: Word, isMemorized: Boolean)->Unit,
){
    var testMode by remember { mutableStateOf(TestMode.None) }
    val onCreateNewCategory by remember { mutableStateOf(false) }

    var autoOption by remember {
        mutableStateOf(WordBookAutoOption(
            autoScroll = false,
            autoScrollDelay = 1500L,
            autoWordSpeak = false,
            autoMeanSpeak = false
        ))
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        CardModeView(
            tts = tts,
            wordList = wordList,
            categoryList = categoryList,
            wordCategoryList = wordCategoryList,
            autoOption = autoOption,
            onUpdateWordCategory = { wordId, categoryId, onCategory ->  onUpdateWordCategory(wordId, categoryId, onCategory) },
            onChangeMemorize = { word, isMemorized-> onChangeMemorize(word, isMemorized) },
            modifier = modifier.align(Alignment.Center)
        )
    }
}