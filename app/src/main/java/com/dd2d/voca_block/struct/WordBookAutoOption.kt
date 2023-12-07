package com.dd2d.voca_block.struct

import com.dd2d.voca_block.Values.Common.DefaultAutoScrollDelay

/**- 단어장 자동화 옵션
 *- [autoScroll] true 일 경우에만 [autoScrollDelay] 또는 [autoWordSpeak], [autoMeanSpeak] 활성화 가능.
 * [autoWordSpeak] 또는 [autoMeanSpeak] true 인 경우 [autoScrollDelay] 설정 불가: [DefaultAutoScrollDelay] 고정
 * @param[autoScroll] true- 자동으로 다음 단어로 진행.
 * @param[autoScrollDelay] 다음 단어로 넘어가는 시간. 1000L = 1초.
 * @param[autoWordSpeak] true- 단어가 바뀌었을 때 해당 단어를 자동으로 읽어줌.
 * @param[autoMeanSpeak] true- 단어가 바뀌었을 때 해당 단어의 뜻을 자동으로 읽어줌.*/
data class WordBookAutoOption(
    var autoScroll: Boolean = false,
    var autoScrollDelay: Long = DefaultAutoScrollDelay,
    var autoWordSpeak: Boolean = false,
    var autoMeanSpeak: Boolean = false,
)