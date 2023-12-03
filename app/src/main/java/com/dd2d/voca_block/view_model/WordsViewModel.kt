package com.dd2d.voca_block.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dd2d.voca_block.Values.CategorySelectorValue
import com.dd2d.voca_block.Values.CategorySelectorValue.AllWord
import com.dd2d.voca_block.Values.CategorySelectorValue.MemorizedWord
import com.dd2d.voca_block.Values.CategorySelectorValue.NotMemorizedWord
import com.dd2d.voca_block.model.WordsModel
import com.dd2d.voca_block.struct.Word
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
            AllWord.id -> { getAllWord() }
            MemorizedWord.id -> { getAllByMemorize(true) }
            NotMemorizedWord.id -> { getAllByMemorize(false) }
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
