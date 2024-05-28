package com.example.voyager_example.ui.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.voyager_example.UoocTabs
import com.example.voyager_example.base.BasicTab
import com.example.voyager_example.vm.StudyViewModel
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.rememberComposePagerState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import voyager_example.shared.generated.resources.Res
import voyager_example.shared.generated.resources.star_bar_star
import voyager_example.shared.generated.resources.study_tab
import kotlin.math.absoluteValue

class StudyScreen(private val index: Int) : BasicTab<StudyViewModel>(create = { get() }) {

    @Composable
    override fun modelOption(model: StudyViewModel, tabNavigator: Navigator): TabOptions {
        var isSelected by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            snapshotFlow { model.rootModel.selected }
                .distinctUntilChanged()
                .map {
                    it == index
                }
                .distinctUntilChanged()
                .collect {
                    isSelected = it
                }
        }
        return TabOptions(
            index = index.toUShort(), title = stringResource(Res.string.study_tab),
            icon = painterResource(if (isSelected) Res.drawable.star_bar_star else Res.drawable.star_bar_star)
        )
    }

    @Composable
    override fun modelContent(model: StudyViewModel, navigator: Navigator, tabbarHeight: Dp) {
        val categoryPagerState = rememberComposePagerState()
        LaunchedEffect(Unit) {
            categoryPagerState.setPageIndex(model.categorySelected.value)
            snapshotFlow { categoryPagerState.getCurrSelectIndexState().value }
                .distinctUntilChanged()
                .collect {
                    model.categorySelected.value = it
                }
        }
        val elevateHeight = 91.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
        var scrollProportion by remember { mutableFloatStateOf(0f) }
        with(LocalDensity.current) {
            val deltaValue = remember { mutableFloatStateOf(0f) }
            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(
                        available: Offset, source: NestedScrollSource
                    ): Offset {
                        val delta = available.y
                        deltaValue.value -= delta
                        val offset = deltaValue.value.toDp()
                        // called when you scroll the content
                        // 通过计算滚动比例，来控制渐变色的显示
                        scrollProportion = if (offset <= 0.dp) 0f else {
                            (offset.toPx().absoluteValue / elevateHeight.toPx().absoluteValue).let {
                                if (it > 1f) 1f else it
                            }
                        }
                        // 处理滚动距离变化，例如打印
                        println("当前滚动偏移量: $offset $scrollProportion")
                        return Offset.Zero
                    }
                }
            }
            BoxWithConstraints(modifier = Modifier.background(Color(0xFFF7F7F7))) {
                Box(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                ) {

                    CompositionLocalProvider(LocalScrollController provides nestedScrollConnection) {
                        ComposePager(
                            model.categoryList.size,
                            Modifier.fillMaxSize(),
                            categoryPagerState,
                            userEnable = false,
                            pageCache = model.categoryList.size,
                        ) {
                            val page = remember { model.categoryList[index] }
                            page.Content()
                        }
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth().height(elevateHeight)
                        // 添加一个渐变色
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFC7DBFF),
                                    Color(0xFFF7F7F7).copy(alpha = scrollProportion)
                                ), start = Offset(0f, 0f), end = Offset(0f, elevateHeight.value)
                            )
                        ).padding(
                            top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                        ).padding(top = 21.dp)
                ) {
                    UoocTabs(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 25.dp, end = 25.dp),
                        pagerState = categoryPagerState,
                        texts = model.categoryList.map { it.model.title })
                    {
                        Spacer(
                            modifier = Modifier.size(8.dp, 3.dp).align(Alignment.BottomCenter)
                                .background(Color(0xFF006FFF), CircleShape)
                        )
                    }
                }
            }
        }
    }
}