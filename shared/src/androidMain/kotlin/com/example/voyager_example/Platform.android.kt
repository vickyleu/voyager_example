package com.example.voyager_example

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.areNavigationBarsVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun getNavigationBarHeight(): Dp {
        return with(LocalDensity.current) {
            if (WindowInsets.areNavigationBarsVisible) {
                WindowInsets.navigationBars.getBottom(this).toDp()
            } else {
                0.dp
            }
        }
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()