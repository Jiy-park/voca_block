package com.dd2d.voca_block.view.word_view_mode.card

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.Values.Common.FontSize
import com.dd2d.voca_block.Values.TestMode
import com.dd2d.voca_block.Values.WordMode
import com.dd2d.voca_block.common_ui.TT
import com.dd2d.voca_block.struct.Category
import com.dd2d.voca_block.struct.Word
import com.dd2d.voca_block.view_model.WordCategoryViewModel

@Composable
fun CardMode (
    modifier: Modifier = Modifier,
    wordList: List<Word>,
    categories: List<Category> = emptyList(),
    wordCategoryViewModel: WordCategoryViewModel,
    onChangeMode: (mode: WordMode)->Unit,
    onChangeMemorize: (word: Word, isMemorized: Boolean)->Unit,
){
    var testMode by remember { mutableStateOf(TestMode.None) }
    var fontSize by remember { mutableStateOf(FontSize.Default) }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        CardModeView(
            list = wordList,
            fontSize = fontSize,
            categoryList = categories,
            wordCategoryViewModel = wordCategoryViewModel,
            onChangeMemorize = { word, isMemorized-> onChangeMemorize(word, isMemorized) },
            modifier = modifier
                .align(Alignment.Center)
        )
        CardModeSettingView(
            fontSize = fontSize,
            testMode = testMode,
            onChangeMode = { mode-> onChangeMode(mode) },
            onChangeFontSize = {
                fontSize = when (fontSize) {
                    FontSize.Small -> { FontSize.Default }
                    FontSize.Default -> { FontSize.Large }
                    FontSize.Large -> { FontSize.Largest }
                    else -> { FontSize.Small }
                }
            },
            onChangeTestMode = {
                testMode = when (testMode) {
                    TestMode.None -> { TestMode.BlindWord }
                    TestMode.BlindWord -> { TestMode.BlindMean }
                    TestMode.BlindMean -> { TestMode.Random }
                    else -> { TestMode.None }
                }
            },
            modifier = modifier.align(Alignment.TopEnd)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CardModeSettingView(
    modifier: Modifier = Modifier,
    fontSize: FontSize,
    testMode: TestMode,
    onChangeMode: (mode: WordMode)->Unit,
    onChangeTestMode: ()->Unit,
    onChangeFontSize: ()->Unit
){
    var onSetting by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
    ){
        IconButton(
            onClick = { onSetting = !onSetting },
            modifier = modifier
                .wrapContentSize()
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                tint = Color.Black,
                contentDescription = "setting",
            )
        }
        AnimatedContent(
            targetState = if(onSetting) 300F else 0F,
            contentAlignment = Alignment.BottomEnd,
            label = "",
            modifier = modifier
                .padding(horizontal = 15.dp)
        ) {
            Column(
                modifier = modifier
                    .height(it.dp)
            ) {
                Button(
                    onClick = { onChangeFontSize() }
                ) {
                    TT(text = "글자크기: $fontSize")
                }
                Button(
                    onClick = { onChangeTestMode() }
                ) {
                    TT(text = "test mode: $testMode")
                }
                Button(
                    onClick = { onChangeMode(WordMode.List) }
                ) {
                    TT(text ="카드모드 -> 리스트모드")
                }
            }
        }
    }
}