package com.dd2d.voca_block.view.main_view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.Values
import com.dd2d.voca_block.Values.Common.MotivationWordMaxLength
import com.dd2d.voca_block.Values.Common.PreferenceValue.DefaultMotivationWord
import com.dd2d.voca_block.Values.Main.Screen
import com.dd2d.voca_block.common_ui.TT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainViewTopPanel(
    modifier: Modifier = Modifier,
    currentTab: String,
    motivationWord: String,
    onChangeMotivationWord: (after: String)->Unit,
){
    var onEdit by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(if (currentTab == Screen.Intro.name) 0.dp else 50.dp)
    ) {
        AnimatedVisibility(
            visible = onEdit,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ) {
            var motiviation by remember { mutableStateOf(motivationWord) }
            TextField(
                value = motiviation,
                onValueChange = {
                    if(it.length < MotivationWordMaxLength) motiviation = it
                },
                leadingIcon = {
                    IconButton(onClick = { onEdit = false }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "")
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onChangeMotivationWord(motiviation)
                            onEdit = false
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "")
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                ),
                textStyle = TextStyle(fontSize = TextUnit.Unspecified),
                modifier = modifier
                    .fillMaxWidth()
            )
        }
        AnimatedVisibility(
            visible = !onEdit,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ) {
            TT(
                text = if(motivationWord.isEmpty()) DefaultMotivationWord else "\"$motivationWord\"",
                fontSize = Values.Common.FontSize.Unspecified,
                modifier = modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        onEdit = true
                    }
            )
        }
    }
}