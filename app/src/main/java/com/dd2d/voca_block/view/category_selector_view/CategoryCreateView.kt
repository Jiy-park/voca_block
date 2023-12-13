package com.dd2d.voca_block.view.category_selector_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.common.Common
import com.dd2d.voca_block.common.TT
import com.dd2d.voca_block.struct.Category
import com.dd2d.voca_block.view.category_selector_view.CategoryCreateError.IsNameBlank
import com.dd2d.voca_block.view.category_selector_view.CategoryCreateError.IsNameDuplication
import com.dd2d.voca_block.view.category_selector_view.CategoryCreateError.IsNone
import com.dd2d.voca_block.view_model.CategoryViewModel

/** [CategoryCreateView] 에서 사용. 새로운 카테고리를 만들 때 에러 상태
 * @property[IsNone] 에러 없음
 * @property[IsNameDuplication] 카테고리 이름이 중복됨
 * @property[IsNameBlank] 이름이 공백*/
sealed class CategoryCreateError{
    object IsNone: CategoryCreateError()
    object IsNameDuplication: CategoryCreateError()
    object IsNameBlank: CategoryCreateError()
}

/** @param[editCategory] null- 단어장을 새로 생성, not null - [editCategory]의 해당하는 값(categoryId)의 단어장을 수정
 * @param[onEndAction] [Category]*/
@Composable
fun CategoryCreateView(
    modifier: Modifier = Modifier,
    editCategory: Int? = null,
    onRequestPopUp: ()->Unit,
    categoryViewModel: CategoryViewModel,
    onEndAction: (new: Category)->Unit,
){
    val categoryList by categoryViewModel.category.collectAsState(initial = emptyList())
    val category = categoryList.find { it.id == editCategory }?: Category()

    /** 수정 모드 시 [editCategory]로 들어온 값에 해당하는 카테고리의 이름, 생성 모드 시 ""*/
    var categoryName by remember { mutableStateOf("") }
    /** 수정 모드 시 [editCategory]로 들어온 값에 해당하는 카테고리의 설명, 생성 모드 시 ""*/
    var categoryDescription by remember { mutableStateOf("") }
    LaunchedEffect(key1 = category){
        categoryName = category.name
        categoryDescription = category.description
    }

    var createError by remember { mutableStateOf<CategoryCreateError>(IsNone) }
    val textFieldColor = TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = Color.Black,
        errorCursorColor = Color.Red.copy(alpha = 0.7F),
        backgroundColor = Color.Transparent,
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Black.copy(alpha = 0.7F),
        errorBorderColor = Color.Red.copy(alpha = 0.7F),
        unfocusedLabelColor = Color.LightGray,
        focusedLabelColor = Color.Black
    )

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(modifier = modifier
            .fillMaxWidth()
        ){
            TT(text = if(editCategory == null) "단어장 만들기" else "단어장 수정", modifier = modifier.align(Alignment.Center))
            IconButton(onClick = { onRequestPopUp() }, modifier = modifier.align(Alignment.CenterStart)) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        ) {
            OutlinedTextField(
                value = categoryName,
                onValueChange = { new->
                    if(new.length <= Common.CategoryNameMaxLength){
                        categoryName = new
                        createError =
                            if(categoryList.any { it.name == categoryName }) IsNameDuplication
                            else IsNone
                    }
                },
                label = { TT(text = "단어장 이름", color = Color.LightGray) },
                singleLine = true,
                isError = createError != IsNone,
                colors = textFieldColor,
                modifier = modifier
                    .fillMaxWidth()
            )
            if(createError != IsNone){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = modifier.fillMaxWidth()
                ){
                    Icon(imageVector = Icons.Default.Warning, tint = Color.Red.copy(alpha = 0.7F), contentDescription = "")
                    when(createError){
                        IsNone -> {  }
                        IsNameDuplication -> TT(text = "이미 존재하는 단어장 이름입니다.", color = Color.Red.copy(alpha = 0.7F))
                        IsNameBlank -> TT(text = "단어장의 이름을 적어주세요.", color = Color.Red.copy(alpha = 0.7F))
                    }
                }
            }
            OutlinedTextField(
                value = categoryDescription,
                onValueChange = { categoryDescription = it },
                label = { TT(text = "단어장 설명", color = Color.LightGray) },
                colors = textFieldColor,
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1F)
            )
            TextButton(
                onClick = {
                    if(categoryName.isBlank()) createError = IsNameBlank
                    else onEndAction(category.copy(name = categoryName, description = categoryDescription))
                },
                modifier = modifier.fillMaxWidth()
            ) {
                TT(text = "완료")
            }
        }
    }
}