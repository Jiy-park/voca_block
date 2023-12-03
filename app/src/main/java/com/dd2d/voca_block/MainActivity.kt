package com.dd2d.voca_block

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import com.dd2d.voca_block.common_ui.TT
import com.dd2d.voca_block.common_ui.theme.Voca_blockTheme
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
        val pref = Preference(baseContext)
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
                        pref = pref
                    )
                }
            }
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
