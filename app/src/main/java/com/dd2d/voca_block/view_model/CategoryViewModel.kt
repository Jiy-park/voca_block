package com.dd2d.voca_block.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dd2d.voca_block.DB
import com.dd2d.voca_block.model.CategoryModel
import com.dd2d.voca_block.struct.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class CategoryViewModel(db: DB): ViewModel() {
    private val model = CategoryModel(db)

    private var _categoryList = flowOf<List<Category>>()
    val category: Flow<List<Category>>
        get() = _categoryList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _categoryList = model.getAll()
            Log.d("LOG_CHECK", "CategoryViewModel :: () -> complete init categoryViewModel")
        }
    }

    fun addCategory(new: Category){
        viewModelScope.launch(Dispatchers.IO){
            model.insert(new)
        }
    }

    fun deleteCategory(target: Category){
        viewModelScope.launch(Dispatchers.IO){
            model.delete(target)
        }
    }

    fun updateCategory(target: Category){
        viewModelScope.launch(Dispatchers.IO){
            model.update(target)
        }
    }
}