package com.dd2d.voca_block.view.word_book_view

/** [WordMode] 에 존재하는 모드의 종류 리스트.
 * @see[WordMode] */
val WordModeValues = listOf(WordMode.List, WordMode.Card)
/** [WordBookView] 에서 단어가 보여질 형태의 종류.
 * @property[List] 단어를 목록 형태로 보여줌.
 * @property[Card] 단어를 카드 형태로 보여줌. 기본값.
 * @see[WordBookView]*/
sealed class WordMode(val toKor: String){
    /** 단어를 목록 형태로 보여줌.
     * @see[WordMode]*/
    object List: WordMode("목록형"){
        const val a = ""
    }

    /** 단어를 카드 형태로 보여줌. 기본값.
     * @see[WordMode]*/
    object Card: WordMode("카드형"){
        /** 카드의 스와이프 동작 종류.
         * @property[Up] 위로 스와이프.
         * @property[None] 스와이프 하지 않음.
         * @property[Down] 아래로 스와이프.
         * @see[UpperSwipeTrigger]
         * @see[LowerSwipeTrigger]*/
        sealed class SwipeTo{
            object Up: SwipeTo()
            object None: SwipeTo()
            object Down: SwipeTo()
        }

        const val CardMaxWidth = 250F
        const val CardMinWidth = 50F
        const val CardMaxHeight = 300F
        const val CardMinHeight = 250F

        /**- 카드 테스트에서 뷰를 위로 스와이프 했음을 인지하는 최소 값 = -150F.
         *- 스와이프의 결과로 받은 position 의 값이 해당 [UpperSwipeTrigger] 값보다 작으면 위로 스와이프 */
        const val UpperSwipeTrigger = -150F
        /**- 카드 테스트에서 뷰를 아래로 스와이프 했음을 인지하는 최소 값 = 150F.
         *- 스와이프의 결과로 받은 position 의 값이 해당 [LowerSwipeTrigger] 값보다 크면 아래로 스와이프 */
        const val LowerSwipeTrigger = 150F

        /** 카드 테스트 뷰의 스와이프 범위*/
        val VerticalSwipeRange = -(CardMaxHeight -10F)..0F
    }
}