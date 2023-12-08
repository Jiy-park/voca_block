package com.dd2d.voca_block.view.word_book_view.category_selector

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.common.FontSize
import com.dd2d.voca_block.common.TT
import com.dd2d.voca_block.struct.Category


@Composable
fun WordBookCategorySelectorView(
    modifier: Modifier = Modifier,
    categoryList: List<Category>,
    isOpenCategorySelector: Boolean,
    onDismissRequest: ()->Unit,
    onClickCategory: (categoryId: Int)->Unit,
    onEditCategory: (after: Category)->Unit,
){
    AnimatedVisibility(
        visible = isOpenCategorySelector,
        enter = slideInHorizontally { -it },
        exit = slideOutHorizontally { -it },
        label = ""
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            IconButton(onClick = { onDismissRequest() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "")
            }
            LazyColumn(
                state = rememberLazyListState(),
                contentPadding = PaddingValues(10.dp),
            ){
                CategorySelectorValue.values().forEach { value->
                    item{
                        CategorySelectorViewItem(
                            item = Category(id = value.id, name = value.toName, description = value.description),
                            onClick = { categoryId->
                                onClickCategory(categoryId)
                                onDismissRequest()
                            }
                        )
                    }
                }
                items(items = categoryList){ category->
                    var onEditMode by remember { mutableStateOf(false) }
                    CategorySelectorViewItem(
                        item = category,
                        onClick = { categoryId->
                            onClickCategory(categoryId)
                            onDismissRequest()
                        },
                        use = true,
                        onEditMode = onEditMode,
                        onChangeEditMode = { onEditMode = !onEditMode }
                    )
                }
            }
        }
    }
}


/** 시용자가 만든 카테고리를 표시. 해당 카테고리는 수정 가능
 * @see[CategorySelectorViewItem] */
@Composable
fun CategorySelectorViewItem(
    modifier: Modifier = Modifier,
    item: Category,
    onClick: (categoryId: Int)->Unit,
    use: Boolean = false,
    onEditMode: Boolean,
    onChangeEditMode: (()->Unit)? = null,
){

    val editModeHeight by animateFloatAsState(
        targetValue = if(onEditMode) 300F else 0F,
        label = ""
    )
    TextButton(onClick = { onClick(item.id) }, enabled = !onEditMode) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()
            ){
                Column(
                    modifier = modifier.wrapContentWidth()
                ) {
                    TT(text = item.name, textAlign = TextAlign.Left, fontSize = FontSize.Default)
                    TT(text = item.description, textAlign = TextAlign.Left, fontSize = FontSize.Smallest)
                }
                if(use){
                    IconButton(onClick = { onChangeEditMode!!() }) {
                        Icon(imageVector = if(onEditMode) Icons.Default.Close else Icons.Default.Edit, tint = Color.Black,contentDescription = "")
                    }
                }
            }
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(editModeHeight.dp)
                    .background(color = Color.White)
            ){
                TT(text = "CategorySelectorViewItem")
            }
        }
    }
}

/**- 기본적으로 존재하는 카테고리를 표시. 해당 카테고리는 수정 불가능.
 * @see[CategorySelectorValue]
 * @see[CategorySelectorViewItem]*/
@Composable
fun CategorySelectorViewItem(
    modifier: Modifier = Modifier,
    item: Category,
    onClick: (categoryId: Int)->Unit,
){
    TextButton(onClick = { onClick(item.id) }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ){
            Column(
                modifier = modifier.wrapContentWidth()
            ) {
                TT(text = item.name, textAlign = TextAlign.Left, fontSize = FontSize.Default)
                TT(text = item.description, textAlign = TextAlign.Left, fontSize = FontSize.Smallest)
            }
        }
    }
}