package com.dd2d.voca_block.common

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.dd2d.voca_block.common.FontSize.Default
import com.dd2d.voca_block.common.FontSize.Large
import com.dd2d.voca_block.common.FontSize.Largest
import com.dd2d.voca_block.common.FontSize.Small
import com.dd2d.voca_block.common.FontSize.Smallest
import com.dd2d.voca_block.common.FontSize.Unspecified

/**
 * @property[Unspecified] TextUnit.Unspecified
 * @property[Smallest] 10.sp
 * @property[Small] 13.sp
 * @property[Default] 18.sp
 * @property[Large] 20.sp
 * @property[Largest] 25.sp
 * */
enum class FontSize(val toKor: String, val size: TextUnit){
    Unspecified("적용형", TextUnit.Unspecified),
    Smallest("매우 작음", 10.sp),
    Small("작음", 13.sp),
    Default("보통", 18.sp),
    Large("큼", 20.sp),
    Largest("매우 큼", 25.sp),
}
val LocalFontSize = compositionLocalOf { FontSize.Default }