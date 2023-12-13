package com.dd2d.voca_block.common

import com.dd2d.voca_block.common.AppState.Error
import com.dd2d.voca_block.common.AppState.Intro
import com.dd2d.voca_block.common.AppState.Main

/** 앱의 현재 상태.
 * @property[Intro] 인트로 진행 중.
 * @property[Main] 인트로 종료 후, 메인 진행 중
 * @property[Error] 앱 실행 중 에러 발생*/
enum class AppState{
    Intro, Main, Error, CreateCategory, EditCategory
}

enum class SubScreen(val tabName: String){
    Intro("인트로"),
    CategorySelector("단어장 선택"),
    CategoryCreate("단어장 만들기"),
    CategoryEdit("단어장 수정")
}

enum class MainScreen(val tabName: String){
    UserProfile("내정보"),
    MemoryBook("암기장"),
    WordBook("단어장"),
    Setting("설정")
}