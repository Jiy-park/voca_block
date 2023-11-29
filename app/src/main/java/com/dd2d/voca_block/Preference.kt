package com.dd2d.voca_block

import android.content.Context
import com.dd2d.voca_block.Values.Common.PreferenceValue.CheeringWord
import com.dd2d.voca_block.Values.Common.PreferenceValue.DefaultCheeringWord
import com.dd2d.voca_block.Values.Common.PreferenceValue.PrefName

class Preference(context: Context){
    private val pref = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE)

    fun setCheeringWord(cheering: String){
        val edit = pref.edit()
        edit.putString(CheeringWord, cheering)
        edit.apply()
    }

    fun getCheeringWord() = pref.getString(CheeringWord, DefaultCheeringWord)?: DefaultCheeringWord
}