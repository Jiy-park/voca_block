package com.dd2d.voca_block.struct

import com.dd2d.voca_block.Values.Common.DefaultAutoScrollDelay

/**- 단어장 자동화 옵션
 *- [autoScroll] true 일 경우에만 [autoWordSpeak] 또는 [autoMeanSpeak] 활성화 가능.
 * 또한 [autoScrollDelay] 의 값은 [D                                                                                                       efaultAutoScrollDelay]로 고정
 * @param[autoScroll] true- 자동으로 다음 단어로 진행.
 * @param[autoScrollDelay] 다음 단어로 넘어가는 시간. 1000L = 1초.
 * @param[autoWordSpeak] true- 단어가 바뀌었을 때 해당 단어를 자동으로 읽어줌.
 * @param[autoMeanSpeak] true- 단어가 바뀌었을 때 해당 단어의 뜻을 자동으로 읽어줌.*/
data class WordBookAutoOption(
    var autoScroll: Boolean,
    var autoScrollDelay: Long,
    var autoWordSpeak: Boolean,
    var autoMeanSpeak: Boolean
){
    fun checkOption(){
        if(autoWordSpeak || autoMeanSpeak){
            autoScrollDelay = DefaultAutoScrollDelay
        }

    }
}