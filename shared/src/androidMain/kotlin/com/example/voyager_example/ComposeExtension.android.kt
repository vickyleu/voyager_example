package com.example.voyager_example

import androidx.compose.foundation.Indication
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
actual fun ripple(
    bounded: Boolean,
    radius: Dp,
    color: Color
): Indication {
//    return rememberRipple(bounded = bounded, radius = radius, color = color)
    // compose 1.6.10~1.6.20-dev rememberRipple is deprecated error, should use androidx.compose.material3.ripple instead,

    return androidx.compose.material3.ripple(color = color, radius = radius, bounded = bounded)
}