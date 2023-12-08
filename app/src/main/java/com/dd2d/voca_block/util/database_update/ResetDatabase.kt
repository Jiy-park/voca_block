package com.dd2d.voca_block.util.database_update

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.dd2d.voca_block.DB
import com.dd2d.voca_block.common.WordType
import com.dd2d.voca_block.util.log

@Composable
@Preview()
fun ResetDatabase(){
    val context = LocalContext.current
    val db = DB.getInstance(context)
    LaunchedEffect(key1 = Unit){
        deleteFile(context)
        writeFile(context = context, dataList = data){
            readFile(context)
        }
        dbUpdateByText(context = context, db = db, wordType = WordType.EnKr){
            it.log("end")
        }
    }
}