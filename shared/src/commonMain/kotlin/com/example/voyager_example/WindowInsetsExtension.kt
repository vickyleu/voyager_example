package com.example.voyager_example

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



val WindowInsets.statusBarHeight: Dp
    @Composable
    get() {
        return with(LocalDensity.current) {
            with(WindowInsets.statusBars) insets@{
                getTop(this@with)
            }.toDp()
        }
    }


val WindowInsets.navigationHeight: Dp
    @Composable
    get() {
        return with(LocalDensity.current) {
            with(WindowInsets.navigationBars) insets@{
                this.getBottom(this@with).toDp()
            }.let {
                if (it > 0.dp) it.plus(30.dp) else 50.dp
            }
        }
    }