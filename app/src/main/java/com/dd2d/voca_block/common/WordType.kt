package com.dd2d.voca_block.common

/** 단어의 종류, [type].first - word, [type].second - mean
 * @param[type] 단어의 타입을 Pair 형태로 반환
 * @property[EnKr] 영어->한국어
 * @see[DetailType]*/
enum class WordType(val type: Pair<DetailType, DetailType>){
    EnKr(DetailType.En to DetailType.Kr),
}
/**
 * 언어의 종류,
 * @param[description] 언어 설명
 * @property[En] 영어
 * @property[Kr] 한국아*/
enum class DetailType(val description: String){
    En("영어"), Kr("한국어")
}