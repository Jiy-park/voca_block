package com.dd2d.voca_block.common

import com.dd2d.voca_block.common.Common.CategoryCreateMode
import com.dd2d.voca_block.common.Common.CategoryDescriptionMaxLength
import com.dd2d.voca_block.common.Common.CategoryNameMaxLength
import com.dd2d.voca_block.common.Common.DatabaseName
import com.dd2d.voca_block.common.Common.DefaultAutoScrollDelay
import com.dd2d.voca_block.common.Common.DefaultAutoScrollDelayRange
import com.dd2d.voca_block.common.Common.DefaultMotivationWord
import com.dd2d.voca_block.common.Common.DoubleBackPressInterval
import com.dd2d.voca_block.common.Common.IntroDuration
import com.dd2d.voca_block.common.Common.MotivationWordMaxLength
import com.dd2d.voca_block.struct.WordBookAutoOption
import com.dd2d.voca_block.view.category_selector_view.CategoryCreateView
/**
 *- 앱에서 전체적으로 사용하는 변수
 * @property[IntroDuration] 인트로 화면 지속 시간
 * @property[DatabaseName] room 데이터베이스 이름
 * @property[MotivationWordMaxLength] 메인 화면 중간의 동기부여의 한마디 의 최대 길이
 * @property[CategoryNameMaxLength] 카테고리 이름 최대 길이
 * @property[CategoryDescriptionMaxLength] 카테고리 설명 최대 길이
 * @property[DefaultAutoScrollDelay] [WordBookAutoOption] 의 autoScrollDelay 기본값
 * @property[DefaultAutoScrollDelayRange] WordBook 자동 스크롤 딜레이 설정 범위.
 * @property[DoubleBackPressInterval] 뒤로가기 두번 눌러 종료에 사용되는 시간 간격
 * @property[DefaultMotivationWord] 동기부여의 한마디 기본값
 * @property[CategoryCreateMode] [CategoryCreateView]의 새 단어장 생성 모드 시 사용 값.*/
object Common{
    const val IntroDuration = 0L
    const val DatabaseName = "voca_block"
    const val MotivationWordMaxLength = 20
    const val CategoryNameMaxLength = 20
    const val CategoryDescriptionMaxLength = 40
    const val DefaultAutoScrollDelay = 1500L
    val DefaultAutoScrollDelayRange = 1000F..3500F
    const val DoubleBackPressInterval = 400L
    const val DefaultMotivationWord = "탭해서 동기부여의 한마디를 적어봐요!"
    const val CategoryCreateMode = -1
}