package com.dd2d.voca_block.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dd2d.voca_block.Values.Main.Screen
import com.dd2d.voca_block.view.memory_book_view.MemoryBookView
import com.dd2d.voca_block.view.setting_view.SettingView
import com.dd2d.voca_block.view.user_profile_view.UserProfileView
import com.dd2d.voca_block.view.word_book_view.WordBookView
import com.dd2d.voca_block.view_model.CategoryViewModel
import com.dd2d.voca_block.view_model.WordCategoryViewModel
import com.dd2d.voca_block.view_model.WordsViewModel

fun NavGraphBuilder.mainGraph(
    navController: NavController,
    wordsViewModel: WordsViewModel,
    categoryViewModel: CategoryViewModel,
    wordCategoryViewModel: WordCategoryViewModel
){
    composable(route = Screen.UserProfile.name) {
        UserProfileView(
            wordsViewModel = wordsViewModel
        )
    }
    composable(route = Screen.MemoryBook.name) {
        MemoryBookView()
    }
    composable(route = Screen.WordBook.name) {
        WordBookView(
            wordsViewModel = wordsViewModel,
            categoryViewModel = categoryViewModel,
            wordCategoryViewModel = wordCategoryViewModel
        )
    }
    composable(route = Screen.Setting.name) {
        SettingView()
    }
}