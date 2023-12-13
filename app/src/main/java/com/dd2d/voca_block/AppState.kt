package com.dd2d.voca_block

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

////enum class AppStateValue{
////    Intro, Main, Error, CreateCategory, EditCategory
////}
//class AppState {
//    private var _current = MutableStateFlow<AppStateValue>(AppStateValue.Intro)
//    val current: StateFlow<AppStateValue>
//        get() = _current.asStateFlow()
//
//    fun setCurrentState(state: AppStateValue){
//        _current.value = state
//    }
//
//    fun endIntro(){
//        _current.value = AppStateValue.Main
//    }
//
//}
//
//sealed class AppStateValue(val description: String){
//    object Intro: AppStateValue("인트로")
//    object Main: AppStateValue("메인")
//    object Error: AppStateValue("에러")
//    object CreateCategory: AppStateValue("단어장 만들기")
//    object EditCategory: AppStateValue("단어장 수정")
//}
