
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dd2d.voca_block.IntroView
import com.dd2d.voca_block.Values.Main.View.MemoryBook
import com.dd2d.voca_block.Values.Main.View.Setting
import com.dd2d.voca_block.Values.Main.View.UserProfile
import com.dd2d.voca_block.Values.Main.View.Words
import com.dd2d.voca_block.common_ui.TT
import com.dd2d.voca_block.view.UserProfileView
import com.dd2d.voca_block.view_model.CategoryViewModel
import com.dd2d.voca_block.view_model.WordCategoryViewModel
import com.dd2d.voca_block.view_model.WordsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = true)
fun T(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    var currentTab by remember { mutableStateOf(Screen.Loading.name) }
    var cheeringWord by remember { mutableStateOf("으악") }
    val ripple = rememberRipple(bounded = true, radius = 100.dp, color = Color.Red)

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                MainTop(
                    modifier = modifier,
                    currentTab = currentTab,
                    cheeringWord = cheeringWord,
                ){ after->
                    cheeringWord = after
                }
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = Color.White,
                    contentPadding = PaddingValues(0.dp),
                    modifier = modifier
                        .height(if(currentTab == Screen.Loading.name) 0.dp else 50.dp)
                ) {
                    Screen.values().drop(1).forEach { screen ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = modifier
                                .weight(1F)
                                .fillMaxHeight()
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = ripple
                                ) {
                                    currentTab = screen.name
                                    navController.navigate(route = screen.name) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                .offset {
                                    if (currentTab == screen.name) IntOffset(0, -30)
                                    else IntOffset(0, 0)
                                }
                        ) {
                            TT(text = screen.tabName)
                        }
                    }
                }
            },
            modifier = modifier
        ) { innerPadding->
            NavHost(
                navController = navController,
                startDestination = Screen.Loading.name,
                modifier = modifier
                    .padding(innerPadding)
            ){
                composable(route = Screen.Loading.name){
                    IntroView(onFail = {}) {
                        currentTab = Screen.Main.name
                        navController.navigate(route = Screen.Main.name){
                            popUpTo(route = Screen.Main.name)
                        }
                    }
                }
                mainGraph()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTop(
    modifier: Modifier = Modifier,
    currentTab: String = "test",
    cheeringWord: String = "test",
    onChangeCheering: (after: String)->Unit = {  },
){
    var onEdit by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(if (currentTab == Screen.Loading.name) 0.dp else 50.dp)
    ) {
        AnimatedVisibility(
            visible = onEdit,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ) {
            var cheering by remember { mutableStateOf(cheeringWord) }
            TextField(
                value = cheering,
                onValueChange = { cheering = it },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onChangeCheering(cheering)
                            onEdit = false
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "")
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                ),
                modifier = modifier
                    .fillMaxWidth()
            )
        }
        AnimatedVisibility(
            visible = !onEdit,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ) {
            TT(
                text = "\"$cheeringWord\"",
                modifier = modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        onEdit = true
                    }
            )
        }
    }
}

fun NavGraphBuilder.mainGraph(
    navController: NavController,
    wordViewModel: WordsViewModel,
    categoryViewModel: CategoryViewModel,
    wordCategoryViewModel: WordCategoryViewModel
){
    composable(route = UserProfile.name) {
        UserProfileView(wordList = , onClickDay = )
    }
    composable(route = MemoryBook.name) {
//        MemoryBookView()
    }
    composable(route = Words.name) {
//        WordBookView()
    }
    composable(route = Setting.name) {
//        SettingView()
    }
}

enum class Screen(val tabName: String = ""){
    Loading, Main("메인"),Second("두번째"),Third("세번째")
}

@Composable
fun Main(
    modifier: Modifier = Modifier,
    onClick: ()->Unit,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Red)
    ){
        Button(onClick = { onClick() }) {
            TT(text = "main")
        }
    }
}

@Composable
fun Second(
    modifier: Modifier = Modifier,
    onClick: ()->Unit,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Green)
    ){
        Button(onClick = { onClick() }) {
            TT(text = "second")
        }
    }
}

@Composable
fun Third(
    modifier: Modifier = Modifier,
    onClick: ()->Unit,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Blue)
    ){
        Button(onClick = { onClick() }) {
            TT(text = "third")
        }
    }
}
