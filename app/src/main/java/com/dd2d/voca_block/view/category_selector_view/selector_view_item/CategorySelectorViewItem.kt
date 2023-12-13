package com.dd2d.voca_block.view.category_selector_view.selector_view_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.dd2d.voca_block.common.FontSize
import com.dd2d.voca_block.common.TT
import com.dd2d.voca_block.struct.Category

@Composable
fun CategorySelectorViewItem(
    modifier: Modifier = Modifier,
    item: Category,
    onClick: (categoryId: Int)->Unit,
    isEditable: Boolean = false,
    onClickEditCategory: (categoryId: Int)->Unit = {  },
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

            if(isEditable){
                IconButton(onClick = { onClickEditCategory(item.id) }) {
                    Icon(imageVector = Icons.Default.Edit, tint = Color.Black,contentDescription = "")
                }
            }
        }
    }
}