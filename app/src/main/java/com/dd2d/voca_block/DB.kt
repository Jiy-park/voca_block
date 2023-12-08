package com.dd2d.voca_block

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dd2d.voca_block.common.Common.DatabaseName
import com.dd2d.voca_block.DB.Companion.getInstance
import com.dd2d.voca_block.common.WordType
import com.dd2d.voca_block.struct.Category
import com.dd2d.voca_block.struct.CategoryDao
import com.dd2d.voca_block.struct.Word
import com.dd2d.voca_block.struct.WordCategory
import com.dd2d.voca_block.struct.WordCategoryDao
import com.dd2d.voca_block.struct.WordDao
import com.dd2d.voca_block.util.database_update.data
import com.dd2d.voca_block.util.database_update.dbUpdateByText
import com.dd2d.voca_block.util.database_update.readFile
import com.dd2d.voca_block.util.database_update.writeFile
import com.dd2d.voca_block.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomCallback(val context: Context): RoomDatabase.Callback(){
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            val database = getInstance(context)
            writeFile(context = context, dataList = data){
                readFile(context)
            }
            dbUpdateByText(context = context, db = database, wordType = WordType.EnKr){
                it.log("end")
            }
            database.categoryDao().insert(Category(id = 0, name = "북마크", description = "북마크한 단어 목록을 불러옵니다."))
        }
    }
}

@Database(entities = [Word::class , Category::class, WordCategory::class], version = 1, exportSchema = true)
abstract class DB: RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun categoryDao(): CategoryDao
    abstract fun wordCategoryDao(): WordCategoryDao

    companion object{
        private var instance: DB? = null

        fun getInstance(context: Context) = instance ?: synchronized(this){
            Room.databaseBuilder(context, DB::class.java, DatabaseName)
                .addCallback(RoomCallback(context))
                .build()
                .also {
                    Log.d("LOG_CHECK", "DB :: getInstance() -> complete init voca_block")
                    instance = it
                }
        }
    }
}

