package com.dd2d.voca_block.view.word_book_view.setting_view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.common.FontSize
import com.dd2d.voca_block.struct.WordBookAutoOption
import com.dd2d.voca_block.view.word_book_view.WordMode
import com.dd2d.voca_block.view.word_book_view.setting_view.setting_view_item.WordBookSettingViewItem_AutoMeanSpeak
import com.dd2d.voca_block.view.word_book_view.setting_view.setting_view_item.WordBookSettingViewItem_AutoOption
import com.dd2d.voca_block.view.word_book_view.setting_view.setting_view_item.WordBookSettingViewItem_AutoScrollDelay
import com.dd2d.voca_block.view.word_book_view.setting_view.setting_view_item.WordBookSettingViewItem_AutoWordSpeak
import com.dd2d.voca_block.view.word_book_view.setting_view.setting_view_item.WordBookSettingViewItem_FontSize
import com.dd2d.voca_block.view.word_book_view.setting_view.setting_view_item.WordBookSettingViewItem_WordMode

@Composable
fun WordBookSettingView(
    modifier: Modifier = Modifier,
    isOpenSetting: Boolean,
    wordMode: WordMode,
    fontSize: FontSize,
    autoOption: WordBookAutoOption,
    onChangeWordMode: (mode: WordMode)->Unit,
    onChangeFontSize: (size: FontSize)->Unit,
    onChangeAutoOption: (option: WordBookAutoOption)->Unit,
){
    val settingViewHeight by animateDpAsState(
        targetValue = if(isOpenSetting) 300.dp else 0.dp,
        label = ""
    )
    val autoOptionViewHeight by animateDpAsState(
        targetValue = if(autoOption.autoScroll) 300.dp else 0.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy),
        label = ""
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(settingViewHeight)
            .background(color = Color.White)
            .padding(vertical = 10.dp)
            .verticalScroll(state = rememberScrollState())
    ) {
        WordBookSettingViewItem_FontSize(modifier, fontSize) { onChangeFontSize(it) }
        WordBookSettingViewItem_WordMode(modifier, wordMode) { onChangeWordMode(it) }
        WordBookSettingViewItem_AutoOption(modifier, autoOption) { onChangeAutoOption(it) }
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxWidth()
                .height(autoOptionViewHeight)
        ) {
            WordBookSettingViewItem_AutoScrollDelay(modifier, autoOption) { onChangeAutoOption(it) }
            WordBookSettingViewItem_AutoWordSpeak(modifier, autoOption) { onChangeAutoOption(it) }
            WordBookSettingViewItem_AutoMeanSpeak(modifier, autoOption) { onChangeAutoOption(it) }
        }
    }
}