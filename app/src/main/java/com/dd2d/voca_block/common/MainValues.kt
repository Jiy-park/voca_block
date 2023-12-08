package com.dd2d.voca_block.common

/** 앱의 현재 상태.
 * @property[Intro] 인트로 진행 중.
 * @property[Main] 인트로 종료 후, 메인 진행 중*/
enum class AppState{
    Intro, Main,
}

enum class Screen(val tabName: String){
    Intro("인트로"),
    UserProfile("내정보"),
    MemoryBook("암기장"),
    WordBook("단어장"),
    Setting("설정")
}