package com.dd2d.voca_block

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.dd2d.voca_block.Values.CategorySelectorValue.AllWord
import com.dd2d.voca_block.Values.CategorySelectorValue.MemorizedWord
import com.dd2d.voca_block.Values.CategorySelectorValue.NotMemorizedWord
import com.dd2d.voca_block.Values.Common.DatabaseName
import com.dd2d.voca_block.Values.Common.Day
import com.dd2d.voca_block.Values.Common.DefaultAutoScrollDelay
import com.dd2d.voca_block.Values.Common.DefaultAutoScrollDelayRange
import com.dd2d.voca_block.Values.Common.DetailType
import com.dd2d.voca_block.Values.Common.DetailType.En
import com.dd2d.voca_block.Values.Common.DetailType.Kr
import com.dd2d.voca_block.Values.Common.DoubleBackPressInterval
import com.dd2d.voca_block.Values.Common.FontSize
import com.dd2d.voca_block.Values.Common.FontSize.Default
import com.dd2d.voca_block.Values.Common.FontSize.Large
import com.dd2d.voca_block.Values.Common.FontSize.Largest
import com.dd2d.voca_block.Values.Common.FontSize.Small
import com.dd2d.voca_block.Values.Common.FontSize.Smallest
import com.dd2d.voca_block.Values.Common.FontSize.Unspecified
import com.dd2d.voca_block.Values.Common.IntroDuration
import com.dd2d.voca_block.Values.Common.Month
import com.dd2d.voca_block.Values.Common.Month.Temp
import com.dd2d.voca_block.Values.Common.MotivationWordMaxLength
import com.dd2d.voca_block.Values.Common.PreferenceValue
import com.dd2d.voca_block.Values.Common.PreferenceValue.DefaultMotivationWord
import com.dd2d.voca_block.Values.Common.PreferenceValue.MotivationWord
import com.dd2d.voca_block.Values.Common.PreferenceValue.PrefName
import com.dd2d.voca_block.Values.Common.WordType
import com.dd2d.voca_block.Values.Common.WordType.EnKr
import com.dd2d.voca_block.Values.Main.AppState.Intro
import com.dd2d.voca_block.Values.Main.AppState.Main
import com.dd2d.voca_block.struct.WordBookAutoOption
import com.dd2d.voca_block.view_model.WordsViewModel


object Values {
    object Main{
        /** 앱의 연재 상태.
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
    }

    /**
     *- 앱에서 전체적으로 사용하는 변수
     * @property[IntroDuration] 인트로 화면 지속 시간
     * @property[DatabaseName] room 데이터베이스 이름
     * @property[MotivationWordMaxLength] 메인 화면 중간의 동기부여의 한마디 의 최대 길이
     * @property[DefaultAutoScrollDelay] [WordBookAutoOption] 의 autoScrollDelay 기본값
     * @property[DefaultAutoScrollDelayRange] WordBook 자동 스크롤 딜레이 설정 범위.
     * @property[DoubleBackPressInterval] 뒤로가기 두번 눌러 종료에 사용되는 시간 간격
     * @property[PreferenceValue] Preference 용
     * @property[WordType] 단어의 종류, [WordType], [DetailType] 참조
     * @property[FontSize] 앱 전반적인 폰트 사이즈
     * @property[Month] 캘린더에 사용되는 월 변수
     * @property[Day] 캘린더에 사용되는 요일 변수*/
    object Common{
        const val IntroDuration = 1110L
        const val DatabaseName = "voca_block"
        const val MotivationWordMaxLength = 20
        const val CategoryNameMaxLength = 20
        const val DefaultAutoScrollDelay = 1500L
        val DefaultAutoScrollDelayRange = 500F..3000F
        const val DoubleBackPressInterval = 400L

        /** @property[PrefName] = "pref"
         * @property[MotivationWord] = "motivation"
         * @property[DefaultMotivationWord] = "탭해서 동기부여의 한마디를 적어봐요!"*/
        object PreferenceValue{
            const val PrefName = "pref"
            const val MotivationWord = "motivation"
            const val DefaultMotivationWord = "탭해서 동기부여의 한마디를 적어봐요!"
        }

        /** 단어의 종류, [type.first] - word, [type.second] - mean
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

        /**
         * @property[Unspecified] TextUnit.Unspecified
         * @property[Smallest] 10.sp
         * @property[Small] 13.sp
         * @property[Default] 18.sp
         * @property[Large] 20.sp
         * @property[Largest] 25.sp
         * */
        enum class FontSize(val toKor: String, val size: TextUnit){
            Unspecified("적용형", TextUnit.Unspecified),
            Smallest("매우 작음", 10.sp),
            Small("작음", 13.sp),
            Default("보통", 18.sp),
            Large("큼", 20.sp),
            Largest("매우 큼", 25.sp),
        }
        val LocalFontSize = compositionLocalOf { Default }

        /** @property[Temp] 각 ordinal 을 월에 맞추기 위해 있음. 사용X*/
        enum class Month(val toKor: String) {
            Temp("Do Not Use"), Jan("1월"), Feb("2월"),
            Mar("3월"), Apr("4월"), May("5월"),
            Jun("6월"), Jul("7월"), Aug("8월"), Sep("9월"),
            Oct("10월"), Nov("11월"), Dec("12월")
        }

        enum class Day(val toKor: String, val toIndex: Int) {
            Sun("일요일", 0), Mon("월요일", 1), Tue("화요일", 2),
            Wed("수요일", 3), Thu("목요일", 4), Fri("금요일", 5), Sat("토요일", 6),
        }
    }

    enum class TestMode{
        None, BlindWord, BlindMean, Random
    }


    /**- 카테고리 선택에서 기본적으로 갖는 값.
     * @property [AllWord] 전체 단어
     * @property [MemorizedWord] 외운 단어
     * @property [NotMemorizedWord] 못외운 단어
     * @see [WordsViewModel.selectByCategoryId]*/
    enum class CategorySelectorValue(val id: Int, val toName: String, val description: String){
        AllWord(0, "전체 단어장", "전체 단어 목록을 불러옵니다."),
        MemorizedWord(-1, "외운 단어", "외운 단어 목록을 불러옵니다."),
        NotMemorizedWord(-2, "못외운 단어", "외우지 못한 단어 목록을 불러옵니다."),
    }

    sealed class WordFilter{
        object None: WordFilter()
        object Bookmark: WordFilter()
        object Memorize: WordFilter()
        object Custom: WordFilter()
    }

    val WordModeValues = listOf(WordMode.List, WordMode.Card)

    sealed class WordMode(val toKor: String){

        object List: WordMode("목록형"){
            const val a = ""
        }

        object Card: WordMode("카드형"){
            sealed class SwipeTo{
                object Up: SwipeTo()
                object None: SwipeTo()
                object Down: SwipeTo()
            }

            const val CardMaxWidth = 250F
            const val CardMinWidth = 50F
            const val CardMaxHeight = 300F
            const val CardMinHeight = 250F

            /**- 카드 테스트에서 뷰를 위로 스와이프 했음을 인지하는 최소 값.
             *- 스와이프의 결과로 받은 position 의 값이 해당 [UpperSwipeTrigger] 값보다 작으면 위로 스와이프 */
            const val UpperSwipeTrigger = -150F
            /**- 카드 테스트에서 뷰를 아래로 스와이프 했음을 인지하는 최소 값.
             *- 스와이프의 결과로 받은 position 의 값이 해당 [LowerSwipeTrigger] 값보다 크면 아래로 스와이프 */
            const val LowerSwipeTrigger = 150F

            /** 카드 테스트 뷰의 스와이프 범위*/
            val VerticalSwipeRange = -(CardMaxHeight -10F)..0F
        }
    }

    object Util{
        object LogWord{
            const val id = 1.shl(1)
            const val word = 1.shl(2)
            const val mean = 1.shl(3)
            const val wordType = 1.shl(4)
            const val bookmark = 1.shl(5)
            const val memorize = 1.shl(6)
        }
    }
}