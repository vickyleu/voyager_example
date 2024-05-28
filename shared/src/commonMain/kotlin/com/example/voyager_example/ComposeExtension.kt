package com.example.voyager_example

import androidx.compose.foundation.Indication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior

@Composable
expect fun ripple(
    bounded: Boolean = true,
    radius: Dp = Dp.Unspecified,
    color: Color = Color.Unspecified
): Indication


fun lightenColor(color: Color, amount: Float): Color {
    // 提取颜色的RGBA值
    val r = (color.red * 255 + amount * 255).coerceIn(0f, 255f)
    val g = (color.green * 255 + amount * 255).coerceIn(0f, 255f)
    val b = (color.blue * 255 + amount * 255).coerceIn(0f, 255f)
    val alpha = color.alpha

    // 返回一个新的颜色
    return Color(r / 255, g / 255, b / 255, alpha)
}


@OptIn(InternalVoyagerApi::class)
val LocalNavigatorController = compositionLocalOf(structuralEqualityPolicy<Navigator>()) {
    Navigator(screens = listOf(), key = "fakeKey", stateHolder = object : SaveableStateHolder {
        @Composable
        override fun SaveableStateProvider(key: Any, content: @Composable () -> Unit) {
        }

        override fun removeState(key: Any) {
        }
    },
        disposeBehavior = NavigatorDisposeBehavior())
}

//val LocalNavigatorController = compositionLocalOf(structuralEqualityPolicy<NavHostController?>()) {
//    null
//}

