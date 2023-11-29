package com.dd2d.voca_block.model

import android.util.Log
import com.dd2d.voca_block.DB
import com.dd2d.voca_block.struct.WordCategory

class WordCategoryModel(db: DB){
    private val model: DB
    init {
        model = db
        Log.d("LOG_CHECK", "WordCategoryModel :: () -> complete init wordCategoryModel")
    }

    fun insert(wordCategory: WordCategory){
        model.wordCategoryDao().insert(wordCategory)
    }

    fun delete(wordCategory: WordCategory){
        model.wordCategoryDao().delete(wordCategory)
    }

    fun update(wordCategory: WordCategory){
        model.wordCategoryDao().update(wordCategory)
    }

    fun getAll() = model.wordCategoryDao().getAll()

    fun getAllByWordId(wordId: Int) = model.wordCategoryDao().getAllByWordId(wordId)

    fun getAllByCategoryId(categoryId: Int) = model.wordCategoryDao().getAllByWordId(categoryId)
}