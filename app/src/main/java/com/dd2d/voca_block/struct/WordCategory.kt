package com.dd2d.voca_block.struct

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Entity(
    tableName = "word_category_table",
    foreignKeys = [
        ForeignKey(
            entity = Word::class,
            parentColumns = ["word_id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["category_id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [Index("wordId"), Index("categoryId")]
)
data class WordCategory(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val wordId: Int,
    val categoryId: Int,
)

@Dao
interface WordCategoryDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wordCategory: WordCategory)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(wordCategory: WordCategory)

    @Delete
    fun delete(wordCategory: WordCategory)

    @Query("SELECT * FROM word_category_table ORDER BY id ASC")
    fun getAll(): Flow<List<WordCategory>>


    /**@return[categoryId]에 해당하는 카테고리의 단어들 id 반환*/
    @Query("SELECT * FROM word_category_table WHERE categoryId =:categoryId ORDER BY wordId ASC")
    fun getAllByCategoryId(categoryId: Int): Flow<List<WordCategory>>

    /**@return[wordId]에 해당하는 단어가 어떤 카테고리에 속하는지 반환 */
    @Query("SELECT * FROM word_category_table WHERE wordId =:wordId ORDER BY categoryId ASC")
    fun getAllByWordId(wordId: Int): Flow<List<WordCategory>>

//    @Query("SELECT * FROM category_table " +
//            "WHERE category_id IN " +
//            "SELECT * FROM word_category_table WHERE wordId")
//    fun testQuery(wordId: Int): Flow<List<Category>>

}