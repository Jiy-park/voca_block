package com.dd2d.voca_block.util.database_update

import android.content.Context
import android.util.Log
import com.dd2d.voca_block.DB
import com.dd2d.voca_block.struct.Word
import com.dd2d.voca_block.view.word_book_view.WordType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/** [FileName] 에 작성된 내용을 토대로 word_table 업데이트
 * @see [writeFile]
 * @see [readFile]*/
suspend fun dbUpdateByText(context: Context, db: DB, wordType: WordType, callback: (String)->Unit){
    try {
        val inStream = context.openFileInput(FileName)
        val reader = BufferedReader(InputStreamReader(inStream))
        val ref = "word_english_amazing_talker3000"
        withContext(Dispatchers.IO){
            var last = ""
            while(true){
                reader.readLine()?.let { line->
                    last = line
                    val s = line.split("\t").dropLast(1)
                    val id = s[0]
                    val word = s[1]
                    val mean = s.subList(2, s.size).joinToString(",")
                    db.wordDao().insert(
                        Word(
                            id = id.toInt(),
                            reference = ref,
                            word = word,
                            mean = mean,
                            wordType = wordType.ordinal,
                            bookmark = false,
                            memorized = false,
                        )
                    )
                }?: break
            }
        callback(last)
        }
    }
    catch (e: IOException){
        Log.e("LOG_CHECK", " :: dbUpdateByText() -> $e")
    }
}
//
//fun generateId(id: String): String{
//    var idField = id
//    while(idField.length != IdFieldSize){ idField = "0$idField" }
//
//    val current = LocalDateTime.now()
//    val formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss")
//    val formattedTime = current.format(formatter)
//
//    return "${formattedTime}_${idField}"
//}
