package com.dd2d.voca_block.view.category_selector_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.common.TT
import com.dd2d.voca_block.struct.Category
import com.dd2d.voca_block.view.category_selector_view.selector_view_item.CategorySelectorViewItem

/** 현재 존재하는 카테고리(= 단어장)의 목록을 보여줌.*/
@Composable
fun CategoryListView(
    modifier: Modifier = Modifier,
    layoutModifier: Modifier,
    categoryList: List<Category>,
    onRequestPopup: ()->Unit,
    onClickCategory: (categoryId: Int)->Unit,
    onClickEditCategory: (categoryId: Int)->Unit,
){
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = layoutModifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 5.dp)
        ) {
            IconButton(onClick = { onRequestPopup() }, modifier = modifier.align(Alignment.CenterStart)) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
            TT(text = "단어장 목록", modifier = modifier.align(Alignment.Center))
        }
        LazyColumn(
            state = rememberLazyListState(),
            contentPadding = PaddingValues(horizontal = 10.dp),
        ){
            CategorySelectorValue.values().forEach { value->
                item{
                    CategorySelectorViewItem(
                        item = Category(id = value.id, name = value.toName, description = value.description),
                        onClick = { categoryId-> onClickCategory(categoryId) }
                    )
                }
            }
            items(items = categoryList.drop(1)){ category->
                CategorySelectorViewItem(
                    modifier = modifier,
                    item = category,
                    onClick = { categoryId-> onClickCategory(categoryId) },
                    isEditable = true,
                    onClickEditCategory = { categoryId-> onClickEditCategory(categoryId) },
                )
            }
        }
    }
}