package com.dd2d.voca_block.util.database_update

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

/** = "words.txt"*/
const val FileName = "words.txt"

/** 리스트를 텍스트 파일로 변환하여 내부 저장소에 저장
 *- [FileName] = "words.txt"
 * @see [readFile]
 * @see [dbUpdateByText]*/
suspend fun writeFile(
    context: Context,
    dataList: List<String>,
    callback: suspend ()->Unit
) {
    try {
        val outputStream: FileOutputStream = context.openFileOutput(FileName, Context.MODE_APPEND)

        dataList.forEach { data ->
            outputStream.write("$data\n".toByteArray())
        }

        Log.d("LOG_CHECK", " :: writeToFile() -> end write")
        outputStream.close()
        coroutineScope {
            callback()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
/** [FileName] 에 해당하는 파일을 읽어옴
 * @see [writeFile]
 * @see[dbUpdateByText]*/
suspend fun readFile(context: Context){
    try {
        val inStream = context.openFileInput(FileName)
        val reader = BufferedReader(InputStreamReader(inStream))
        withContext(Dispatchers.IO){
            while(true){
                reader.readLine()?.split("\t")?.dropLast(1) ?: break
            }
        }
    }
    catch (e: IOException){
        Log.e("LOG_CHECK", " :: readFile() -> $e")
    }
}

fun deleteFile(context: Context){
    context.deleteFile(FileName)
}