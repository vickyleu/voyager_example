package com.example.voyager_example

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

interface Platform {
    val name: String

    @Composable
    fun getNavigationBarHeight(): Dp
}

expect fun getPlatform(): Platform