package com.dd2d.voca_block.model

import android.util.Log
import com.dd2d.voca_block.DB
import com.dd2d.voca_block.struct.Word

class WordsModel(db: DB){
    private val wordModel: DB
    init {
        wordModel = db
        Log.d("LOG_CHECK", "WordsModel :: () -> complete init wordModel")
    }

    fun getAllWord() = wordModel.wordDao().getAll()

    fun getAllByCategoryId(categoryId: Int) = wordModel.wordDao().getAllByCategoryId(categoryId)

    fun getMemorizedWord(isMemorized: Boolean) = wordModel.wordDao().getMemorized(isMemorized)


    fun insertWord(word: Word){
        wordModel.wordDao().insert(word)
    }

    fun deleteWord(word: Word){
        wordModel.wordDao().delete(word)
    }

    fun updateWord(word: Word){
        try {
            wordModel.wordDao().update(word)
        } catch (e: Exception){
            Log.e("LOG_CHECK", "WordsModel :: updateWord() -> $e")
        }
    }
}