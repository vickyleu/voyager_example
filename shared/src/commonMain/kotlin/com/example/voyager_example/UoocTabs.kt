package com.example.voyager_example

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lt.compose_views.compose_pager.ComposePagerState
import com.lt.compose_views.pager_indicator.PagerIndicator
import com.lt.compose_views.pager_indicator.TextPagerIndicator
import com.lt.compose_views.util.rememberMutableStateOf
import kotlin.math.abs
import kotlin.math.roundToInt

fun Float/*percentage*/.getPercentageValue(startValue: Float, endValue: Float): Float =
    if (startValue == endValue) startValue
    else (endValue - startValue) * this + startValue

fun Float/*percentage*/.getPercentageValue(startValue: Color, endValue: Color): Color =
    Color(
        alpha = getPercentageValue(startValue.alpha, endValue.alpha),
        red = getPercentageValue(startValue.red, endValue.red),
        green = getPercentageValue(startValue.green, endValue.green),
        blue = getPercentageValue(startValue.blue, endValue.blue),
    )

@Composable
fun BoxScope.UoocTabs(
    modifier: Modifier = Modifier,
    pagerState: ComposePagerState,
    texts: List<String>,
    indicator: @Composable () -> Unit
) {
    val offsetPercentWithSelectFlow =
        remember { pagerState.createChildOffsetPercentFlow() }
    val selectIndexFlow = remember { pagerState.createCurrSelectIndexFlow() }
    /*TextPagerIndicator(
        texts = texts,
        offsetPercentWithSelectFlow = offsetPercentWithSelectFlow,
        selectIndexFlow = selectIndexFlow,
        fontSize = 14.sp,
        selectFontSize = 17.sp,
        textColor = Color(0xFF999999),
        selectTextColor = Color(0xFF333333),
        onIndicatorClick = {
            pagerState.setPageIndexWithAnimate(it)
        },
        selectIndicatorItem = {


        },
        modifier = modifier
            .wrapContentWidth()
            .height(35.dp),
        margin = 28.dp,
    )*/
    val fontSize = 14.sp
    val selectFontSize = 17.sp
    val textColor = Color(0xFF999999)
    val selectTextColor = Color(0xFF333333)

    val density = LocalDensity.current
    val fontPx by remember(fontSize, density) {
        mutableStateOf(density.run { fontSize.toPx() })
    }
    val selectFontPx by remember(selectFontSize, density) {
        mutableStateOf(density.run { selectFontSize.toPx() })
    }

    with(LocalDensity.current) {
        PagerIndicator(
            size = texts.size,
            offsetPercentWithSelectFlow = offsetPercentWithSelectFlow,
            selectIndexFlow = selectIndexFlow,
            indicatorItem = { index ->
                val selectIndex by selectIndexFlow.collectAsState(0)
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .clickable {
                        if (index != selectIndex) {
                            pagerState.setPageIndexWithAnimate(index)
                        }
                    }) {
                    val offsetPercentWithSelect by offsetPercentWithSelectFlow.collectAsState(0f)
                    val (size, color) = remember(
                        index,
                        selectIndex,
                        offsetPercentWithSelect,
                        selectFontSize,
                        fontSize,
                        textColor,
                        selectTextColor,
                    ) {
                        val percent = abs(selectIndex + offsetPercentWithSelect - index)
                        if (percent > 1f)
                            return@remember fontSize to textColor
                        density.run {
                            (
                                    (percent.getPercentageValue(selectFontPx, fontPx))
                                        .roundToInt().toSp())
                        } to (
                                percent.getPercentageValue(selectTextColor, textColor)
                                )
                    }
                    Text(
                        text = texts[index],
                        fontSize = size,
                        color = color,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            },
            selectIndicatorItem = {
                var width by rememberMutableStateOf(0.dp)
                val offsetPercentWithSelect by offsetPercentWithSelectFlow.collectAsState(0f)
                val selectIndex by selectIndexFlow.collectAsState(0)
                with(LocalDensity.current) {
                    LaunchedEffect(texts, offsetPercentWithSelect, selectIndex) {
                        width = density.run {
                            //当前选中的指示器宽度
                            val widthRun =
                                indicatorsInfo.getIndicatorSize(selectIndex).toFloat()
                            if (offsetPercentWithSelect == 0f)
                                return@run widthRun.toDp()
                            val index = selectIndex + if (offsetPercentWithSelect > 0) 1 else -1
                            //将要选中的指示器宽度
                            val toWidth = indicatorsInfo.getIndicatorSize(index).toFloat()
                            //通过百分比计算出实际宽度
                            abs(offsetPercentWithSelect).getPercentageValue(widthRun, toWidth)
                                .toDp()
                        }
                    }
                    Box(modifier = Modifier.fillMaxHeight()) {
                        Spacer(
                            modifier = Modifier
                                .size(width, 3.dp)
                                .align(Alignment.BottomCenter)
                        )
                        indicator()
                    }
                }
            },
            modifier = modifier
                .wrapContentWidth()
                .height(35.dp),
            margin = 8.dp,
            orientation = Orientation.Horizontal,
            userCanScroll = false
        )
    }


}