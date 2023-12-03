package com.dd2d.voca_block.struct

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("word_id")
    val id: Int,
    val reference: String,
    val word: String,
    val mean: String,
    val wordType: Int,
    var bookmark: Boolean,
    var memorized: Boolean,
)

@Dao
interface WordDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: Word)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(word: Word)

    @Delete
    fun delete(word: Word)

    @Query("SELECT * FROM word_table ORDER BY word_id ASC")
    fun getAll(): Flow<List<Word>>

    @Query("SELECT * FROM word_table WHERE memorized =:isMemorized ORDER BY word_id ASC")
    fun getMemorized(isMemorized: Boolean): Flow<List<Word>>

    @Query("SELECT * FROM word_table WHERE bookmark =:isBookmark ORDER BY word_id ASC")
    fun getBookmarkWord(isBookmark: Boolean): Flow<List<Word>>

    @Query("SELECT * \n" +
            "FROM word_table\n" +
            "INNER JOIN (\n" +
            "    SELECT wordId\n" +
            "    FROM word_category_table\n" +
            "    WHERE categoryId = :categoryId \n" +
            ") AS target\n" +
            "ON word_id = target.wordId\n" +
            "ORDER BY target.wordId ASC")
    fun getAllByCategoryId(categoryId: Int): Flow<List<Word>>
}

//val sampleWordList = mutableListOf<Word>(
//    Word(id = "000000", word = "longlonglonglonglonglonglong", mean = "버리다버리다버리다버리다버리다버리다버리다", wordType = WordType.EnKr.ordinal, bookmark = false, memorized = false),
//    Word(id = "000001", word = "abandon", mean = "버리다", wordType = WordType.EnKr.ordinal, bookmark = false, memorized = false),
//    Word(id = "000002", word = "crash", mean = "도산", wordType = WordType.EnKr.ordinal, bookmark = false, memorized = false),
//    Word(id = "000003", word = "document", mean = "서류", wordType = WordType.EnKr.ordinal, bookmark = false, memorized = false),
//    Word(id = "000004", word = "feel", mean = "만져보다", wordType = WordType.EnKr.ordinal, bookmark = false, memorized = false),
//    Word(id = "000005", word = "prefer", "오히려 ~mean= 을 좋아하다", wordType = WordType.EnKr.ordinal, bookmark = false, memorized = false),
//    Word(id = "000006", word = "lose", mean = "잃다", wordType = WordType.EnKr.ordinal, bookmark = false, memorized = false),
//    Word(id = "000007", word = "suppose", mean = "상상하다", wordType = WordType.EnKr.ordinal, bookmark = false, memorized = false),
//    Word(id = "000008", word = "vague", mean = "막연한", wordType = WordType.EnKr.ordinal, bookmark = false, memorized = false),
//    Word(id = "000009", word = "write", mean = "쓰다", wordType = WordType.EnKr.ordinal, bookmark = false, memorized = false),
//    Word(id = "000010", word = "thorough", mean = "완전한", wordType = WordType.EnKr.ordinal, bookmark = false, memorized = false),
//    Word(id = "000011", word = "naive", mean = "소박한", wordType = WordType.EnKr.ordinal, bookmark = false, memorized = false),
//    Word(id = "000012", word = "operate", mean = "움직이다", wordType = WordType.EnKr.ordinal, bookmark = false, memorized = false),
//)