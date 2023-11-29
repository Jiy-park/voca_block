package com.dd2d.voca_block.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dd2d.voca_block.model.WordCategoryModel
import com.dd2d.voca_block.struct.WordCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class WordCategoryViewModel(wordCategoryModel: WordCategoryModel): ViewModel() {
    private val model = wordCategoryModel

    private var _selectedCategory = flowOf(emptyList<WordCategory>())
    val selectedCategory: Flow<List<WordCategory>>
        get() = _selectedCategory

    init {
        _selectedCategory = model.getAll()
        Log.d("LOG_CHECK", "WordCategoryViewModel :: () -> complete init wordCategoryViewModel")
    }

    fun addNewWordCategory(wordId: Int, categoryId: Int){
        viewModelScope.launch(Dispatchers.IO){
            model.insert(WordCategory(id = "${wordId}_$categoryId", wordId = wordId, categoryId = categoryId))
        }
    }

    fun updateWordCategory(wordId: Int, categoryId: Int, onCategory: Boolean){
        val target = WordCategory(id = "${wordId}_$categoryId", wordId = wordId, categoryId = categoryId)
        viewModelScope.launch(Dispatchers.IO){
            if(onCategory){ model.insert(target) }
            else { model.delete(target) }
        }
    }
}