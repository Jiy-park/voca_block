package com.dd2d.voca_block.util

import android.util.Log
import com.dd2d.voca_block.Values
import com.dd2d.voca_block.Values.Util.LogWord
import com.dd2d.voca_block.struct.Word

fun List<*>.log(tag: String = ""): List<*>{
    Log.d("LOG_CHECK", "${this.javaClass.name} :: $tag -> $this \n")
    return this
}

fun <T> T.log(tag: String = ""): T{
    Log.d("LOG_CHECK", " :: $tag -> $this \n")
    return this
}

fun log(tag: String){
    Log.d("LOG_CHECK", " :: log() -> $tag")
}


// id, word, mean, wordType, bookmakr, memorized
//
fun Word.log(
    tag: String = "",
    filter: Int = LogWord.id or LogWord.word or LogWord.mean or LogWord.wordType or LogWord.bookmark or LogWord.memorize
){
    var log = ""

    if(filter and LogWord.id == LogWord.id) { log = "$log ${this.id}" }
    if(filter and LogWord.word == LogWord.word) { log = "$log ${this.word}" }
    if(filter and LogWord.mean == LogWord.mean) { log = "$log ${this.mean}" }
    if(filter and LogWord.wordType == LogWord.wordType) { log = "$log ${this.wordType}" }
    if(filter and LogWord.bookmark == LogWord.bookmark) { log = "$log ${this.bookmark}" }
    if(filter and LogWord.memorize == LogWord.memorize) { log = "$log ${this.memorized}" }

    Log.d("LOG_CHECK", " :: word: $log -> $tag")
}

fun Values.WordFilter.log(tag: String = ""): String{
    val filterName = when(this){
        Values.WordFilter.None -> { "none" }
        Values.WordFilter.Bookmark -> { "bookmark" }
        Values.WordFilter.Memorize -> { "memorize" }
        Values.WordFilter.Custom -> { "custom" }
    }
    Log.d("LOG_CHECK", " :: $tag -> $filterName \n")
    return filterName
}


