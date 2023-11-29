package com.dd2d.voca_block.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dd2d.voca_block.DB
import com.dd2d.voca_block.model.WordsModel
import com.dd2d.voca_block.struct.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class WordsViewModel(db: DB): ViewModel(){
    private val wordsModel = WordsModel(db)

    private var _wordList by mutableStateOf(flowOf<List<Word>>())
    val wordList: Flow<List<Word>>
        get() = _wordList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _wordList = wordsModel.getAllWord()
            Log.d("LOG_CHECK", "WordsViewModel :: () -> complete init wordViewModel")
        }
    }


    fun getMemorizedWord(isMemorized: Boolean = true){
        viewModelScope.launch {
            _wordList = wordsModel.getMemorizedWord(isMemorized)
            Log.d("LOG_CHECK", "WordsViewModel :: getMemorizedWord() -> afdadfasdfs")
        }
    }

    fun updateWordMemorized(word: Word, isMemorized: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
             wordsModel.updateWord(word = word.copy(memorized = isMemorized))
        }
    }

    fun getAllWord(){
      viewModelScope.launch(Dispatchers.IO){
          _wordList = wordsModel.getAllWord()
      }
    }

    fun getAllByCategoryId(categoryId: Int){
        viewModelScope.launch(Dispatchers.IO){
            _wordList = wordsModel.getAllByCategoryId(categoryId)
        }
    }
}