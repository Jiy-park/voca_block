package com.dd2d.voca_block.view.test_book_view

/**- WordBook 에서 사용. 단어 시험의 종류.
 * @property[None] 아무것도 가리지 않음. 단어장 상태와 동일.
 * @property[BlindWord] 단어를 가림. 뜻을 보고 단어를 맞추는 모드.
 * @property[BlindMean] 뜻을 가림. 단어를 보고 뜻을 맞추는 모드.
 * @property[Random] 단어와 뜻 중 랜덤으로 하나를 가림. 가려진 것을 맞추는 모드.
 * @see[TestMode]*/
enum class TestMode{
    None, BlindWord, BlindMean, Random
}
