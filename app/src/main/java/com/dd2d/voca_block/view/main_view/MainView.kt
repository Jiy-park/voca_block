package com.dd2d.voca_block.view.main_view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dd2d.voca_block.Preference
import com.dd2d.voca_block.Values.Main.Screen
import com.dd2d.voca_block.view.intro_view.IntroView
import com.dd2d.voca_block.view.mainGraph
import com.dd2d.voca_block.view_model.CategoryViewModel
import com.dd2d.voca_block.view_model.WordCategoryViewModel
import com.dd2d.voca_block.view_model.WordsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    modifier: Modifier = Modifier,
    wordsViewModel: WordsViewModel,
    categoryViewModel: CategoryViewModel,
    wordCategoryViewModel: WordCategoryViewModel,
    pref: Preference
) {
    val navController = rememberNavController()
    var currentTab by remember { mutableStateOf(Screen.Intro.name) }
    var motivationWord by remember { mutableStateOf(pref.getMotivationWord()) }

    Scaffold(
        topBar = {
            MainViewTopPanel(
                modifier = modifier,
                currentTab = currentTab,
                motivationWord = motivationWord,
            ){ after->
                pref.setMotivationWord(after)
                motivationWord = after
            }
        },
        bottomBar = {
            MainViewBottomPanel(
                modifier = modifier,
                navController = navController,
                currentTab = currentTab,
                onChangeTab = { tab-> currentTab = tab }
            )
        },
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding->
        NavHost(
            navController = navController,
            startDestination = Screen.Intro.name,
            modifier = modifier
                .padding(innerPadding)
        ){
            composable(route = Screen.Intro.name){
                IntroView(onFail = {}) {
                    currentTab = Screen.WordBook.name
                    navController.navigate(route = Screen.WordBook.name){
                        popUpTo(route = Screen.WordBook.name)
                    }
                }
            }
            mainGraph(
                navController = navController,
                wordsViewModel = wordsViewModel,
                categoryViewModel = categoryViewModel,
                wordCategoryViewModel = wordCategoryViewModel,
            )
        }
    }
}