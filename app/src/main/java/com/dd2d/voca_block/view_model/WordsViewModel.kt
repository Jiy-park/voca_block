package com.dd2d.voca_block.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dd2d.voca_block.model.WordsModel
import com.dd2d.voca_block.struct.Word
import com.dd2d.voca_block.view.word_book_view.category_selector.CategorySelectorValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class WordsViewModel(private val wordsModel: WordsModel): ViewModel(){

    private var _wordList by mutableStateOf(flowOf<List<Word>>())
    val wordList: Flow<List<Word>>
        get() = _wordList

    init {
        getAllWord()
        Log.d("LOG_CHECK", "WordsViewModel :: () -> complete init wordViewModel")
    }

    fun updateWordMemorized(word: Word, isMemorized: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
             wordsModel.updateWord(word = word.copy(memorized = isMemorized))
        }
    }

    /**- [wordList] 를 [categoryId] 에 해당하는 단어로 설정
     * 1. [AllWord] 전체 단어
     * 2. [MemorizedWord] 외운 단어
     * 3. [NotMemorizedWord] 못 외운 단어
     * 4. 그 외: [categoryId] 에 해당하는 단어
     * @see [CategorySelectorValue]*/
    fun selectByCategoryId(categoryId: Int){
        when(categoryId){
            CategorySelectorValue.AllWord.id -> { getAllWord() }
            CategorySelectorValue.MemorizedWord.id -> { getAllByMemorize(true) }
            CategorySelectorValue.NotMemorizedWord.id -> { getAllByMemorize(false) }
            else -> { getAllByCategoryId(categoryId) }
        }
    }

    private fun getAllWord(){
      viewModelScope.launch(Dispatchers.IO){
          _wordList = wordsModel.getAllWord()
      }
    }

    private fun getAllByMemorize(isMemorized: Boolean = true){
        viewModelScope.launch {
            _wordList = wordsModel.getMemorizedWord(isMemorized)
        }
    }

    private fun getAllByCategoryId(categoryId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _wordList = wordsModel.getAllByCategoryId(categoryId)
        }
    }
}
