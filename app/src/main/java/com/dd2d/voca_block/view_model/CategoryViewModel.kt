package com.dd2d.voca_block.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dd2d.voca_block.model.CategoryModel
import com.dd2d.voca_block.struct.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class CategoryViewModel(categoryModel: CategoryModel): ViewModel() {
    private val model = categoryModel

    private var _categoryList = flowOf<List<Category>>()
    val category: Flow<List<Category>>
        get() = _categoryList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _categoryList = model.getAll()
            Log.d("LOG_CHECK", "CategoryViewModel :: () -> complete init categoryViewModel")
        }
    }

    fun addNewCategory(name: String, description: String){
        viewModelScope.launch(Dispatchers.IO){
            model.insert(Category(name = name, description = description))
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