package com.dd2d.voca_block.view.category_selector_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.common.TT
import com.dd2d.voca_block.view_model.CategoryViewModel
import com.dd2d.voca_block.view_model.WordsViewModel

@Composable
fun CategorySelectorView(
    modifier: Modifier = Modifier,
    wordsViewModel: WordsViewModel,
    categoryViewModel: CategoryViewModel,
    onRequestPopup: ()->Unit,
    onOpenCategoryCreateView: ()->Unit,
    onOpenCategoryEditView: (categoryId: Int)->Unit,
){
    val categoryList by categoryViewModel.category.collectAsState(initial = emptyList())

    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween){

        CategoryListView(
            layoutModifier = modifier.weight(1F),
            categoryList = categoryList,
            onRequestPopup = { onRequestPopup() },
            onClickCategory = { categoryId->
                wordsViewModel.selectByCategoryId(categoryId)
                onRequestPopup()
            },
            onClickEditCategory = { onOpenCategoryEditView(it) }
        )
        TextButton(
            onClick = { onOpenCategoryCreateView() },
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(5.dp)
        ) {
            TT(text = "단어장 만들기")
        }
    }

}