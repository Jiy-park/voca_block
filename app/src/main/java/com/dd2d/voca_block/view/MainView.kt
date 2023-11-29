package com.dd2d.voca_block.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.dd2d.voca_block.R
import com.dd2d.voca_block.Values.Main
import com.dd2d.voca_block.common_ui.TT
import com.dd2d.voca_block.struct.Category
import com.dd2d.voca_block.util.log


@Composable
fun MainView(
    modifier: Modifier = Modifier,
    cheeringWord: String,
    onChangeCheeringWord: ()->Unit,
    selectedView: Main.View,
    categoryList: List<Category>,
    onClickCategory: (categoryId: Int)->Unit,
    onChangeSelectedView: (Main.View)->Unit,
    content: @Composable () -> Unit,
){
    Log.d("LOG_CHECK", " :: MainView() -> start MainView")

    var isOpenAdditionalView by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        MainTopView(
            cheeringWord = cheeringWord,
            onChangeCheeringWord = { onChangeCheeringWord() },
        )

        Box(modifier = modifier.weight(1F)){
            MainCenterView { content() }
            AdditionalView(
                selectedView = selectedView,
                isOpenAdditionalView = isOpenAdditionalView,
                onCloseAdditionalView = { isOpenAdditionalView = false },
                categoryList = categoryList,
                onClickCategory = { onClickCategory(it) },
                modifier = modifier.align(Alignment.BottomCenter)
            )
        }

        MainBottomView(
            selectedView = selectedView,
            onChangeSelectedView = { selected->
                isOpenAdditionalView = if(selectedView == selected) { !isOpenAdditionalView }
                else {
                    onChangeSelectedView(selected)
                    false
                }
            }
        )
    }
}
@Composable
fun AdditionalView(
    modifier: Modifier = Modifier,
    selectedView: Main.View,
    isOpenAdditionalView: Boolean,
    onCloseAdditionalView: ()->Unit,
    categoryList: List<Category>,
    onClickCategory: (categoryId: Int)->Unit,
){
    AnimatedVisibility(
        visible = isOpenAdditionalView,
        enter = expandVertically { 300 },
        exit = shrinkVertically { 0 },
        modifier = modifier
    ) {
        when(selectedView){
            Main.View.Words -> {
                WordsAdditionalView(
                    onCloseAdditionalView = { onCloseAdditionalView() },
                ){
                    WordsAdditionalViewContent(categoryList = categoryList) { onClickCategory(it) }
                }
            }
            Main.View.UserProfile -> {}
            Main.View.Setting -> {}
            else -> {}
        }
    }
}

@Composable
fun WordsAdditionalViewContent(
    modifier: Modifier = Modifier,
    categoryList: List<Category>,
    onClick: (categoryId: Int)->Unit,
){
    LazyColumn(
        contentPadding = PaddingValues(vertical = 15.dp, horizontal = 30.dp),
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Yellow)
    ){
        item {
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(vertical = 5.dp)
                    .clickable {
                        onClick(0)
                    }
            ) {
                TT(text = "모두 보기")
            }
        }
        itemsIndexed(items = categoryList){_, category->
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(vertical = 5.dp)
                    .clickable {
                        onClick(category.id)
                    }
            ) {
                TT(text = category.name)
            }
        }
        item {
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(vertical = 5.dp)
                    .clickable {
                        onClick(-1)
                    }
            ) {
                TT(text = "새 카테고리")
            }
        }
        
    }
}

@Composable
fun WordsAdditionalView(
    modifier: Modifier = Modifier,
    onCloseAdditionalView: ()->Unit,
    content: @Composable () -> Unit
){
    var height by remember { mutableStateOf(300F) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(height.coerceAtMost(500F).dp)
            .background(Color.DarkGray)
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(color = Color.White)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            if (height < 100F) {
                                onCloseAdditionalView()
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            change.scrollDelta.log("scroll")
                            dragAmount.log("amount")
                            height -= dragAmount.y

                        }
                    )
                }
        ){
            Image(painter = painterResource(id = R.drawable.ic_drag_handle), contentDescription = "drag handel")
        }
        content()
    }
}


@Composable
private fun MainTopView(
    cheeringWord: String,
    onChangeCheeringWord: ()->Unit,
    modifier: Modifier = Modifier
){
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            TT(
                text = "\"$cheeringWord\"",
                modifier = modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { onChangeCheeringWord() }
            )
        }
    }
}

@Composable
private fun MainCenterView(modifier: Modifier = Modifier,  content: @Composable ()->Unit){
    Box(modifier = modifier){
        content()
    }
}

@Composable
private fun MainBottomView(
    selectedView: Main.View,
    onChangeSelectedView: (selected: Main.View)->Unit,
    modifier: Modifier = Modifier
){
    val offsetList = List(Main.View.values().size){ i->
        val mainView = Main.View.values()[i]
        animateIntOffsetAsState(
            targetValue = if (selectedView == mainView) { IntOffset(0, -30) }
            else { IntOffset.Zero },
            label = "",
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color.White)
    ) {
        Main.View.values().forEachIndexed { index, mainView->
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .weight(1F)
                    .offset { offsetList[index].value }
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { onChangeSelectedView(mainView) }
            ){
                TT(
                    text = mainView.toKor,
                    color = if(selectedView == mainView) { Color.Black } else { Color.Gray },
                    modifier = modifier
                )
            }
        }
    }
}