package com.dd2d.voca_block.model

import android.util.Log
import com.dd2d.voca_block.DB
import com.dd2d.voca_block.struct.Category
import com.dd2d.voca_block.util.ll

class CategoryModel(db: DB){
    private val categoryModel: DB
    init {
        categoryModel = db
        Log.d("LOG_CHECK", "categoryModel :: () -> complete init categoryModel")
    }

    fun getAll() = categoryModel.categoryDao().getAll()

    fun insert(category: Category){
        categoryModel.categoryDao().insert(category)
    }

    fun update(category: Category){
        categoryModel.categoryDao().update(category)
    }

    fun delete(category: Category){
        categoryModel.categoryDao().delete(category)
    }

    fun checkDuplicate(name: String) = categoryModel.categoryDao().checkDuplicate(name)

}