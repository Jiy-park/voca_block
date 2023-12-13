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

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("category_id")
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
)

@Dao
interface CategoryDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)

    @Update
    fun update(category: Category)

    @Query("SELECT * FROM category_table ORDER BY category_id ASC")
    fun getAll(): Flow<List<Category>>

    @Query("SELECT COUNT(*) FROM category_table WHERE name =:name")
    fun checkDuplicate(name: String): Int
}
//
//val sampleCategoryList = listOf(
//    Category(0,"북마크"/*, mutableListOf()*/),
//    Category(1,"1"/*, mutableListOf()*/),
//    Category(2,"2"/*, mutableListOf()*/),
//    Category(3,"3"/*, mutableListOf()*/),
//    Category(4,"4"/*, mutableListOf()*/),
//    Category(5,"5"/*, mutableListOf()*/),
//    Category(6,"6"/*, mutableListOf()*/),
//    Category(7,"7"/*, mutableListOf()*/),
//    Category(8,"8"/*, mutableListOf()*/),
//    Category(9,"9"/*, mutableListOf()*/),
//    Category(10,"10"/*, mutableListOf()*/),
//)