package com.dd2d.voca_block

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.dd2d.voca_block.Values.Common.FontSize.Default
import com.dd2d.voca_block.Values.Common.FontSize.Large
import com.dd2d.voca_block.Values.Common.FontSize.Largest
import com.dd2d.voca_block.Values.Common.FontSize.Small
import com.dd2d.voca_block.Values.Common.FontSize.Smallest
import com.dd2d.voca_block.Values.Common.Month.Temp

object Values {
    object Main{
        enum class View(val toKor: String){
            UserProfile("내정보"),
            MemoryBook("암기장"),
            Words("단어장"),
            Setting("설정")
        }

        enum class LoadingProcess{
            InProcess, OnComplete, OnFail
        }
    }

    object Error{
        const val InLoading = "로딩 중 에러 발생."
        const val InInitializeViewModel = "단어장 소환 중 에러 방생."
    }

    object Common{
        /** 아이디에서 해당 단어가 몇번째 단어인지를 표기할 자리
         *- 첫번째 단어일 경우 -> 000001
         *- 열번째 단어일 경우 -> 000010*/
        const val IdFieldSize = 6

        const val IntroDuration = 0L

        const val DatabaseName = "voca_block"

        object PreferenceValue{
            const val PrefName = "pref"

            const val CheeringWord = "cheeringWord"

            const val DefaultCheeringWord = "터치해서 응원의 한마디를 적어봐요!"
        }

        enum class WordType(val description: String){
            EnKr("영단어"),
        }

        /**
         * @property[Smallest] 10.sp
         * @property[Small] 13.sp
         * @property[Default] 18.sp
         * @property[Large] 20.sp
         * @property[Largest] 25.sp
         * */
        enum class FontSize(val size: TextUnit){
            Smallest(10.sp), Small(13.sp), Default(18.sp), Large(20.sp), Largest(25.sp);
        }

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

    sealed class WordFilter{
        object None: WordFilter()
        object Bookmark: WordFilter()
        object Memorize: WordFilter()
        object Custom: WordFilter()
    }

    sealed class WordMode{

        object List: WordMode(){

        }

        object Card: WordMode(){
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