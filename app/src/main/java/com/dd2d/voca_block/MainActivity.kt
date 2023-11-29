package com.dd2d.voca_block

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.Values.Common.IntroDuration
import com.dd2d.voca_block.Values.Error
import com.dd2d.voca_block.Values.Main.LoadingProcess
import com.dd2d.voca_block.Values.Main.View
import com.dd2d.voca_block.Values.WordMode
import com.dd2d.voca_block.common_ui.TT
import com.dd2d.voca_block.common_ui.theme.Voca_blockTheme
import com.dd2d.voca_block.struct.Category
import com.dd2d.voca_block.struct.Word
import com.dd2d.voca_block.util.log
import com.dd2d.voca_block.view.MainView
import com.dd2d.voca_block.view.UserProfileView
import com.dd2d.voca_block.view.word_view_mode.card.CardMode
import com.dd2d.voca_block.view_model.CategoryViewModel
import com.dd2d.voca_block.view_model.WordCategoryViewModel
import com.dd2d.voca_block.view_model.WordsViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pref = Preference(baseContext)
        val db = DB.getInstance(baseContext)
        val wordViewModel = WordsViewModel(db)
        val categoryViewModel = CategoryViewModel(db)
        val wordCategoryViewModel = WordCategoryViewModel(db)

        setContent {
            Voca_blockTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var loadingProcess by remember { mutableStateOf(LoadingProcess.InProcess) }
                    var cheeringWord by remember { mutableStateOf(pref.getCheeringWord()) }

                    when(loadingProcess){
                        LoadingProcess.InProcess->{ InLoadingView{ loadingProcess = it } }
                        LoadingProcess.OnFail->{ ErrorView(Error.InLoading) }
                        LoadingProcess.OnComplete->{
                            CompleteLoadingView(
                                cheeringWord = cheeringWord,
                                wordViewModel = wordViewModel,
                                categoryViewModel = categoryViewModel,
                                wordCategoryViewModel = wordCategoryViewModel,
                            ){
                                cheeringWord = it
                                pref.setCheeringWord(it)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InLoadingView(res: (LoadingProcess)->Unit){
    Log.d("LOG_CHECK", " :: InLoadingView() -> start InLoadingView")
    IntroView(
        onFail = { res(LoadingProcess.OnFail) },
        onComplete = { res(LoadingProcess.OnComplete) }
    )
}

@Composable
fun CompleteLoadingView(
    modifier: Modifier = Modifier,
    wordViewModel: WordsViewModel,
    categoryViewModel: CategoryViewModel,
    wordCategoryViewModel: WordCategoryViewModel,
    cheeringWord: String,
    onChangeCheering: (res: String) -> Unit,
){
    Log.d("LOG_CHECK", " :: CompleteLoadingView() -> start CompleteLoadingView")
    var selectedView by remember { mutableStateOf(View.Words) }
    var selectedMode by remember { mutableStateOf<WordMode>(WordMode.Card) }
    var cheeringWordChangeTrigger by remember { mutableStateOf(false) }
    var addCategoryTrigger by remember { mutableStateOf(false) }
    val wordList by wordViewModel.wordList.collectAsState(initial = emptyList())
    val categoryList by categoryViewModel.category.collectAsState(initial = emptyList())

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        MainView(
            cheeringWord = cheeringWord,
            onChangeCheeringWord = { cheeringWordChangeTrigger = !cheeringWordChangeTrigger },
            selectedView = selectedView,
            onChangeSelectedView = { selected-> selectedView = selected },
            categoryList = categoryList,
            onClickCategory = { categoryId->
                when(categoryId){
                    -1 -> { addCategoryTrigger = !addCategoryTrigger }
                    0 -> { wordViewModel.getAllWord() }
                    else -> { wordViewModel.getAllByCategoryId(categoryId) }
                }
            }
        ) {
            when(selectedView){
                View.UserProfile -> {
                    UserProfileView(wordList = wordList){ year, month, day ->
                        "$year, $month, $day".log("")
                    }
                }
                View.Words -> {
                    when(selectedMode){
                        WordMode.List -> {
                            ListMode(
                                list = wordList,
                                onChangeMode = { mode-> selectedMode = mode},
                                onChangeMemorize = { word, isMemorized-> wordViewModel.updateWordMemorized(word, isMemorized) },
                                modifier = modifier
                            )
                        }
                        WordMode.Card -> {
                            CardMode(
                                wordList = wordList,
                                categories = categoryList,
                                wordCategoryViewModel = wordCategoryViewModel,
                                onChangeMode = { mode-> selectedMode = mode },
                                onChangeMemorize = { word, isMemorized-> wordViewModel.updateWordMemorized(word, isMemorized) },
                                modifier = modifier
                            )
                        }
                    }
                }
                View.Setting->{

                }
                View.MemoryBook->{

                }
            }
        }
        if(cheeringWordChangeTrigger){
            ChangeCheeringWord(
                current = cheeringWord,
                onDismissRequest = { cheeringWordChangeTrigger = false }
            ){ res->
                onChangeCheering(res)
            }
        }
        if(addCategoryTrigger){
            CreateNewCategory(
                onDismissRequest = {
                    it?.let {
                        categoryViewModel.addCategory(Category(name = it))
                    }
                    addCategoryTrigger = false
                },
                checkDuplicate = { checkName->
                    !categoryList.any { it.name == checkName }
                }
            )
        }
    }
}

@Composable
fun ListMode(
    list: List<Word>,
    modifier: Modifier = Modifier,
    onChangeMode: (mode: WordMode)->Unit,
    onChangeMemorize: (word: Word, isMemorized: Boolean)->Unit,
){
    val size = list.size
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ){
        item {
            Button(
                onClick = { onChangeMode(WordMode.Card) }
            ) {
                TT(text = "리스트모드 -> 카드모드")
            }
        }
        itemsIndexed(list){ index,word->
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color.Yellow)
            ){
                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = modifier
                        .matchParentSize()
                        .padding(horizontal = 5.dp)
                ) {
                    Row {
                        Column {
                            TT(text = word.word)
                            TT(text = word.mean)
                        }

                    }
                }
            }
            Spacer(modifier = modifier.height(5.dp))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateNewCategory(
    modifier: Modifier = Modifier,
    checkDuplicate: (categoryName: String)->Boolean,
    onDismissRequest: (String?) -> Unit,
){
    val focusRequest = FocusRequester()
    val keyboard = LocalSoftwareKeyboardController.current
    var categoryName by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(focusRequest){
        focusRequest.requestFocus()
        keyboard?.show()
    }

    AlertDialog(
        onDismissRequest = { onDismissRequest(null) },
        confirmButton = {
            ElevatedButton(
                onClick = {
                    if(checkDuplicate(categoryName)) onDismissRequest(categoryName)
                    else Toast.makeText(context, "이미 존재하는 카테고리입니다.", Toast.LENGTH_SHORT).show()
                }
            ) {
                TT(text = "ok")
            }
        },
        dismissButton = {
            ElevatedButton(onClick = { onDismissRequest(null) }) {
                TT(text = "cancel")
            }
        },
        text = {
            TextField(
                value = categoryName,
                onValueChange = { new-> categoryName = new },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusRequest.requestFocus()
                        keyboard?.hide()
                    }
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .indication(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    )
            )
        },
        modifier = modifier
            .focusRequester(focusRequest)
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ChangeCheeringWord(
    modifier: Modifier = Modifier,
    current: String,
    onDismissRequest: ()->Unit,
    onChange: (res: String)->Unit,
){
    var cheeringWord by remember { mutableStateOf(TextFieldValue(text = current, selection = TextRange(current.length))) }
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequest = FocusRequester()

    LaunchedEffect(focusRequest){
        focusRequest.requestFocus()
        keyboard?.show()
    }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            ElevatedButton(
                onClick = {
                    onChange(cheeringWord.text)
                    onDismissRequest()
                }
            ) {
                TT(text = "ok")
            }
        },
        dismissButton = {
            ElevatedButton(onClick = { onDismissRequest() }) {
                TT(text = "cancel")
            }
        },
        text = {
            TextField(
                value = cheeringWord,
                onValueChange = { new->
                    val newText = new.text.take(10)
                    cheeringWord = new.copy(text = newText, selection = TextRange(newText.length))
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusRequest.requestFocus()
                        keyboard?.hide()
                    }
                ),
                modifier = modifier
                    .fillMaxWidth()
                    .indication(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    )
            )
        },
        modifier = modifier
            .focusRequester(focusRequest)
    )
}

@Composable
fun IntroView(
    modifier: Modifier = Modifier,
    onFail: (error: String?)->Unit,
    onComplete: ()->Unit,
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Icon(imageVector = Icons.Default.Build, tint = Color.White, contentDescription = "임시 이미지")
//        TODO("나중에 로딩 이미지 받을 것")
        TT(text = stringResource(id = R.string.app_name), color = Color.White)
    }
    LaunchedEffect(Unit){
        delay(IntroDuration)
        onComplete()
    }
}

@Composable
fun ErrorView(
    error: String,
    modifier: Modifier = Modifier,
){
    Log.d("LOG_CHECK", " :: ErrorView() -> start ErrorView")
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ){
        Column(
            modifier = modifier
                .fillMaxWidth(0.5F)
                .wrapContentHeight()
                .padding(10.dp)
        ) {
            TT(text = "에러 발생")
            Divider(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
            )
            TT(text = error)
        }
    }
}
