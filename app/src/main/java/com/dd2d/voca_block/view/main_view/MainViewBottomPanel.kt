package com.dd2d.voca_block.view.main_view

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.dd2d.voca_block.Values.Main.AppState
import com.dd2d.voca_block.Values.Main.Screen
import com.dd2d.voca_block.common_ui.TT

@Composable
fun MainViewBottomPanel(
    modifier: Modifier = Modifier,
    appState: AppState,
    navController: NavController,
    currentTab:  NavBackStackEntry?,
){
    val ripple = rememberRipple(bounded = true, radius = 100.dp, color = Color.Red)

    BottomAppBar(
        containerColor = Color.White,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .height(if(appState == AppState.Intro) 0.dp else 50.dp)
    ) {
        // drop(1): IntroView 제외
        Screen.values().drop(1).forEach { screen ->
            val isTabSelected = currentTab?.destination?.route == screen.name
            val animatedTabHeight by animateIntOffsetAsState(
                targetValue = if(isTabSelected) IntOffset(0, -30) else IntOffset(0, 0),
                label = ""
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .weight(1F)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = ripple
                    ) {
                        navController.navigate(route = screen.name) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    .offset { animatedTabHeight }
            ) {
                TT(text = screen.tabName)
            }
        }
    }
}