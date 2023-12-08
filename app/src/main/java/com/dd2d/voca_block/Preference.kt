package com.dd2d.voca_block

import android.content.Context
import com.dd2d.voca_block.common.Common.DefaultAutoScrollDelay
import com.dd2d.voca_block.common.FontSize
import com.dd2d.voca_block.struct.WordBookAutoOption
import com.dd2d.voca_block.view.word_book_view.WordMode
import com.dd2d.voca_block.view.word_book_view.WordModeValues

class Preference(context: Context){
    private val pref = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE)

    fun setMotivationWord(motivation: String){
        val edit = pref.edit()
        edit.putString(MotivationWord, motivation)
        edit.apply()
    }


    fun getMotivationWord() = pref.getString(MotivationWord, "")?: ""

    fun setFontSize(size: FontSize){
        val edit = pref.edit()
        edit.putInt(PrefFontSize, size.ordinal)
        edit.apply()
    }

    fun getFontSize(): FontSize{
        val indexOfFontSize = pref.getInt(PrefFontSize, WordModeValues.indexOf(WordMode.Card))
        return if(indexOfFontSize == -1) FontSize.Default
                else FontSize.values()[indexOfFontSize]
    }

    fun setWordMode(mode: WordMode){
        val edit = pref.edit()
        edit.putInt(PrefWordMode, WordModeValues.indexOf(mode))
        edit.apply()
    }

    fun getWordMode() = WordModeValues[pref.getInt(PrefWordMode, 1)]

    fun setAutoOption(autoOption: WordBookAutoOption){
        val (autoScroll, scrollDelay, speakWord, speakMean) = autoOption
        val edit = pref.edit()
        edit.putBoolean(AutoOption_AutoScroll, autoScroll)
        edit.putLong(AutoOption_ScrollDelay, scrollDelay)
        edit.putBoolean(AutoOption_SpeakWord, speakWord)
        edit.putBoolean(AutoOption_SpeakMean, speakMean)
        edit.apply()
    }

    fun getAutoOption() = WordBookAutoOption(
        autoScroll = pref.getBoolean(AutoOption_AutoScroll, false),
        autoScrollDelay = pref.getLong(AutoOption_ScrollDelay, DefaultAutoScrollDelay),
        autoWordSpeak = pref.getBoolean(AutoOption_SpeakWord, false),
        autoMeanSpeak = pref.getBoolean(AutoOption_SpeakMean, false),
    )

    /** @property[PrefName] Preference 이름.
     * @property[MotivationWord] 앱 상단 중간의 동기부여의 한마디 호출 이름.
     * @property[AutoOption_AutoScroll] WordBook 의 자동 스크롤 사용 여부.
     * @property[AutoOption_ScrollDelay] 자동 스크롤 간격.
     * @property[AutoOption_SpeakWord] 단어를 자동으로 읽어줌.
     * @property[AutoOption_SpeakMean] 단어의 뜻을 자동으로 읽어줌.
     * */
    companion object PreferenceValue{
        private const val PrefName = "pref"
        private const val MotivationWord = "motivation"
        private const val PrefFontSize = "fontSize"
        private const val PrefWordMode = "wordMode"
        private const val AutoOption_AutoScroll = "autoScroll"
        private const val AutoOption_ScrollDelay = "scrollDelay"
        private const val AutoOption_SpeakWord = "speakWord"
        private const val AutoOption_SpeakMean = "speakMean"
    }
}