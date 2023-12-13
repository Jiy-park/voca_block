package com.dd2d.voca_block.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dd2d.voca_block.common.MainScreen
import com.dd2d.voca_block.common.SubScreen
import com.dd2d.voca_block.view.category_selector_view.CategoryCreateView
import com.dd2d.voca_block.view.category_selector_view.CategorySelectorView
import com.dd2d.voca_block.view.setting_view.SettingView
import com.dd2d.voca_block.view.test_book_view.TestBookView
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
    composable(route = MainScreen.UserProfile.name) {
        UserProfileView(
            wordsViewModel = wordsViewModel
        )
    }
    composable(route = MainScreen.MemoryBook.name) {
        TestBookView()
    }
    composable(route = MainScreen.WordBook.name) {
        WordBookView(
            wordsViewModel = wordsViewModel,
            categoryViewModel = categoryViewModel,
            wordCategoryViewModel = wordCategoryViewModel,
            onOpenCategorySelectorView = {
                navController.navigate(SubScreen.CategorySelector.name){
                    popUpTo(navController.previousBackStackEntry?.destination?.route?: MainScreen.WordBook.name)
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
    composable(route = MainScreen.Setting.name) {
        SettingView()
    }

}

fun NavGraphBuilder.categorySelectorGraph(
    navController: NavController,
    wordsViewModel: WordsViewModel,
    categoryViewModel: CategoryViewModel,
    wordCategoryViewModel: WordCategoryViewModel
){
    composable(route = SubScreen.CategorySelector.name){
        CategorySelectorView(
            wordsViewModel = wordsViewModel,
            categoryViewModel = categoryViewModel,
            onRequestPopup = { navController.navigateUp() },
            onOpenCategoryCreateView = {
                navController.navigate(route = SubScreen.CategoryCreate.name){
                    popUpTo(SubScreen.CategorySelector.name)
                    launchSingleTop = true
                    restoreState = true
                }
            },
            onOpenCategoryEditView = { categoryId->
                    navController.navigate(route = "${SubScreen.CategoryEdit.name}/$categoryId"){
                    popUpTo(SubScreen.CategorySelector.name)
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )
    }

    composable(route = SubScreen.CategoryCreate.name){
        CategoryCreateView(
            categoryViewModel = categoryViewModel,
            onRequestPopUp = { navController.navigateUp() },
            onEndAction = { new->
                categoryViewModel.insertCategory(new)
                navController.navigateUp()
            },
        )
    }

    composable(route = "${SubScreen.CategoryEdit.name}/{categoryId}"){ backstack->
        backstack.arguments?.getString("categoryId")?.let { id->
            CategoryCreateView(
                categoryViewModel = categoryViewModel,
                editCategory = id.toInt(),
                onRequestPopUp = { navController.navigateUp() },
                onEndAction = { new->
                    categoryViewModel.updateCategory(new)
                    navController.navigateUp()
                },
            )
        }?: navController.navigateUp()
    }
}