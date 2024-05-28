package com.example.voyager_example.ui.tabs

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.voyager_example.UoocTabs
import com.example.voyager_example.base.BasicTab
import com.example.voyager_example.ripple
import com.example.voyager_example.ui.mooc.CourseTopListScreen
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.rememberComposePagerState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import com.example.voyager_example.vm.HomeViewModel
import voyager_example.shared.generated.resources.Res
import voyager_example.shared.generated.resources.home_tab
import voyager_example.shared.generated.resources.ic_elevate_bg
import voyager_example.shared.generated.resources.ic_home_selected
import voyager_example.shared.generated.resources.ic_mine
import voyager_example.shared.generated.resources.ic_notification
import kotlin.math.absoluteValue

internal val LocalScrollController = compositionLocalOf<NestedScrollConnection> {
    object : NestedScrollConnection {

    }
}

class HomeScreen(private val index: Int) : BasicTab<HomeViewModel>(create = { get() }) {

    @Composable
    override fun modelOption(model: HomeViewModel, tabNavigator: Navigator): TabOptions {
        var isSelected by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            snapshotFlow { model.rootModel.selected }.distinctUntilChanged().map {
                it == index
            }.distinctUntilChanged().collect {
                isSelected = it
            }
        }
        return TabOptions(
            index = index.toUShort(),
            title = stringResource(Res.string.home_tab),
            icon = painterResource(if (isSelected) Res.drawable.ic_home_selected else Res.drawable.ic_mine)
        )
    }

    @Composable
    override fun modelContent(
        model: HomeViewModel,
        navigator: Navigator,
        tabbarHeight: Dp
    ) {
        val categoryPagerState = rememberComposePagerState()
        LaunchedEffect(Unit) {
            categoryPagerState.setPageIndex(model.categorySelected.value)
            snapshotFlow { categoryPagerState.getCurrSelectIndexState().value }
                .distinctUntilChanged()
                .collect {
                    model.categorySelected.value = it
                }
        }
//        val elevateHeight = 304.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
        val elevateHeight =
            91.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 26.dp + 10.dp
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
                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth().height(elevateHeight)
                ) {
                    Image(
                        modifier = Modifier.size(this.maxWidth, this.maxHeight),
                        painter = painterResource(Res.drawable.ic_elevate_bg),
                        contentDescription = stringResource(Res.string.home_tab)
                    )
                }

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
                            val page  = model.categoryList[this.index]
                            navigator.saveableState(page.key,page){
                                page.Content()
                            }
//                            page.Content()
                        }
                    }
                }

                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth().height(elevateHeight)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(
                                top = WindowInsets.statusBars.asPaddingValues()
                                    .calculateTopPadding()
                            ).padding(top = 21.dp)
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .align(Alignment.TopCenter)
                        ) {
                            Row(
                                modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val interactionSource = remember { MutableInteractionSource() }
                                val isPressed by interactionSource.collectIsPressedAsState()
                                val backgroundAlpha = animateFloatAsState(
                                    targetValue = if (isPressed) 0.2f else 0.6f,
                                    label = "backgroundAlpha"
                                ).value
                                Box(modifier = Modifier.weight(1f)
                                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = ripple(
                                            color = Color.Blue.copy(alpha = backgroundAlpha),
                                        )
                                    ) {
                                        navigator.push(CourseTopListScreen())
                                    }
                                    .clip(RoundedCornerShape(16.dp))
                                    .padding(
                                        start = 15.dp,
                                        end = 4.dp,
                                        top = 4.dp,
                                        bottom = 4.dp
                                    )
                                ) {
                                    Row {
                                        Image(
                                            painter = painterResource(Res.drawable.ic_mine),
                                            contentDescription = "mine"
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Box(
                                            modifier = Modifier.wrapContentSize()
                                                .background(
                                                    Color(0XFF006FFF),
                                                    RoundedCornerShape(12.dp)
                                                )
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Text("搜索", color = Color.White, fontSize = 12.sp)
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(15.dp))
                                Image(
                                    modifier = Modifier.size(26.dp),
                                    painter = painterResource(Res.drawable.ic_notification),
                                    contentDescription = "notification"
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth().padding(top = 13.dp)
                            ) {
                                UoocTabs(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .padding(start = 25.dp, end = 25.dp),
                                    pagerState = categoryPagerState,
                                    texts = model.categoryList.map { it.model.title },
                                ) {
                                    Spacer(
                                        modifier = Modifier.size(8.dp, 3.dp)
                                            .align(Alignment.BottomCenter)
                                            .background(Color(0xFF006FFF), CircleShape)
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }

}