package com.dd2d.voca_block.view.main_view

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dd2d.voca_block.Preference
import com.dd2d.voca_block.common.AppState
import com.dd2d.voca_block.common.Common.DoubleBackPressInterval
import com.dd2d.voca_block.common.MainScreen
import com.dd2d.voca_block.common.SubScreen
import com.dd2d.voca_block.view.categorySelectorGraph
import com.dd2d.voca_block.view.intro_view.IntroView
import com.dd2d.voca_block.view.mainGraph
import com.dd2d.voca_block.view_model.CategoryViewModel
import com.dd2d.voca_block.view_model.WordCategoryViewModel
import com.dd2d.voca_block.view_model.WordsViewModel

/** 뒤로가기 두번으로 앱 종료*/
@Composable
fun DoubleBackPressToFinish(){
    val context = LocalContext.current
    var firstPress = 0L

    BackHandler {
        if(System.currentTimeMillis() - firstPress < DoubleBackPressInterval){
            (context as Activity).finish()
        }
        else {
            firstPress = System.currentTimeMillis()
            Toast.makeText(context, "한 번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    modifier: Modifier = Modifier,
    wordsViewModel: WordsViewModel,
    categoryViewModel: CategoryViewModel,
    wordCategoryViewModel: WordCategoryViewModel,
) {
    var appState by remember { mutableStateOf(AppState.Intro) }
    val navController = rememberNavController()
    val currentTab by navController.currentBackStackEntryAsState()

    val context = LocalContext.current
    val pref = Preference(context)
    var motivationWord by remember { mutableStateOf(pref.getMotivationWord()) }

    DoubleBackPressToFinish()

    Scaffold(
        topBar = {
            MainViewTopPanel(
                modifier = modifier,
                appState = appState,
                motivationWord = motivationWord,
            ){ after->
                pref.setMotivationWord(after)
                motivationWord = after
            }
        },
        bottomBar = {
            MainViewBottomPanel(
                modifier = modifier,
                appState = appState,
                navController = navController,
                currentTab = currentTab,
            )
        },
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding->
        NavHost(
            navController = navController,
            startDestination = if(appState == AppState.Intro) SubScreen.Intro.name else MainScreen.WordBook.name,
            modifier = modifier.padding(innerPadding)
        ){
            composable(route = SubScreen.Intro.name){
                IntroView(
                    onFail = {  },
                    onComplete = {
                        appState = AppState.Main
                        navController.popBackStack()
                        navController.navigate(route = MainScreen.WordBook.name){
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            mainGraph(
                navController = navController,
                wordsViewModel = wordsViewModel,
                categoryViewModel = categoryViewModel,
                wordCategoryViewModel = wordCategoryViewModel,
            )
            categorySelectorGraph(
                navController = navController,
                wordsViewModel = wordsViewModel,
                categoryViewModel = categoryViewModel,
                wordCategoryViewModel = wordCategoryViewModel,
            )
        }
    }
}