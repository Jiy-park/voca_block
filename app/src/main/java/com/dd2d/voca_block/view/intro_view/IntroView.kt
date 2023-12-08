package com.dd2d.voca_block.view.intro_view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.dd2d.voca_block.common.Common.IntroDuration
import com.dd2d.voca_block.R
import com.dd2d.voca_block.common.TT
import kotlinx.coroutines.delay

@Composable
fun IntroView(
    modifier: Modifier = Modifier,
    onFail: (error: String?)->Unit,
    onComplete: ()->Unit,
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Icon(imageVector = Icons.Default.Build, tint = Color.White, contentDescription = "임시 이미지")
//        TODO("나중에 로딩 이미지 받을 것")
        TT(text = stringResource(id = R.string.app_name), color = Color.White)
    }
    LaunchedEffect(Unit){
        delay(IntroDuration)
        onComplete()
    }
}