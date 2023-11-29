package com.dd2d.voca_block

import android.content.Context
import com.dd2d.voca_block.Values.Common.PreferenceValue.MotivationWord
import com.dd2d.voca_block.Values.Common.PreferenceValue.PrefName

class Preference(context: Context){
    private val pref = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE)

    fun setMotivationWord(motivation: String){
        val edit = pref.edit()
        edit.putString(MotivationWord, motivation)
        edit.apply()
    }

    fun getMotivationWord() = pref.getString(MotivationWord, "")?: ""
}