package com.dd2d.voca_block.view.word_book_view

import com.dd2d.voca_block.view.word_book_view.DetailType.En
import com.dd2d.voca_block.view.word_book_view.DetailType.Kr
import com.dd2d.voca_block.view.word_book_view.WordType.EnKr
import java.util.Locale

/** 단어의 종류.
 *- [type].first - word
 *- [type].second - mean
 * @param[type] 단어의 타입을 Pair 형태로 반환
 * @property[EnKr] 영어-한국어
 * @see[DetailType]*/
enum class WordType(val type: Type){
    EnKr(En to Kr),
}
/**언어의 종류,
 * @param[description] 언어 설명
 * @property[En] 영어
 * @property[Kr] 한국아
 * @see[WordType]*/
enum class DetailType(val description: String, val locale: Locale){
    Kr("한국어", Locale.KOREAN),
    En("영어", Locale.US),
}

infix fun DetailType.to(value: DetailType) = Type(this, value)

/** */
data class Type(
    val wordType: DetailType,
    val meanType: DetailType
)