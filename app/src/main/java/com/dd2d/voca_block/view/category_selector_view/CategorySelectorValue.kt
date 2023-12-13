package com.dd2d.voca_block.view.category_selector_view

import com.dd2d.voca_block.view.category_selector_view.CategorySelectorValue.AllWord
import com.dd2d.voca_block.view.category_selector_view.CategorySelectorValue.MemorizedWord
import com.dd2d.voca_block.view.category_selector_view.CategorySelectorValue.NotMemorizedWord
import com.dd2d.voca_block.view_model.WordsViewModel

/**- 카테고리 선택에서 기본적으로 갖는 값.
 * @property [AllWord] 전체 단어
 * @property [MemorizedWord] 외운 단어
 * @property [NotMemorizedWord] 못외운 단어
 * @see [WordsViewModel.selectByCategoryId]*/
enum class CategorySelectorValue(val id: Int, val toName: String, val description: String){
    AllWord(0, "전체 단어장", "전체 단어 목록을 불러옵니다."),
    MemorizedWord(-1, "외운 단어", "외운 단어 목록을 불러옵니다."),
    NotMemorizedWord(-2, "못외운 단어", "외우지 못한 단어 목록을 불러옵니다."),
    BookMark(1, "북마크", "북마크한 단어 목록을 불러옵니다.")
}