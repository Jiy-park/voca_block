package com.dd2d.voca_block

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dd2d.voca_block.common.theme.Voca_blockTheme
import com.dd2d.voca_block.model.CategoryModel
import com.dd2d.voca_block.model.WordCategoryModel
import com.dd2d.voca_block.model.WordsModel
import com.dd2d.voca_block.view.main_view.MainView
import com.dd2d.voca_block.view_model.CategoryViewModel
import com.dd2d.voca_block.view_model.WordCategoryViewModel
import com.dd2d.voca_block.view_model.WordsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = DB.getInstance(baseContext)
        val wordsViewModel = WordsViewModel(WordsModel(db))
        val categoryViewModel = CategoryViewModel(CategoryModel(db))
        val wordCategoryViewModel = WordCategoryViewModel(WordCategoryModel(db))

        setContent {
            Voca_blockTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(
                        modifier = Modifier,
                        wordsViewModel = wordsViewModel,
                        categoryViewModel = categoryViewModel,
                        wordCategoryViewModel = wordCategoryViewModel,
                    )
                }
            }
        }
    }
}