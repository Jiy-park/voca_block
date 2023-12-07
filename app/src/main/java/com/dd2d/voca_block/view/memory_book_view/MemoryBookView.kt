package com.dd2d.voca_block.view.memory_book_view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dd2d.voca_block.common_ui.TT

@Composable
fun MemoryBookView(
    modifier: Modifier = Modifier,
){
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()){
        TT(text = "Memory View")
    }
}