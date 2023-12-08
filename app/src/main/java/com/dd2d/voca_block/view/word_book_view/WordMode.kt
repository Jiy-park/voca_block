package com.dd2d.voca_block.view.word_book_view

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