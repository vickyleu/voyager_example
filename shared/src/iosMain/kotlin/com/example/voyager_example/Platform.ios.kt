package com.example.voyager_example

import androidx.compose.runtime.Composable
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIDevice
import platform.UIKit.navigationController

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    @OptIn(ExperimentalForeignApi::class)
    @Composable
    override fun getNavigationBarHeight(): Dp {
        val controller = LocalUIViewController.current
        return controller.navigationController?.navigationBar?.frame?.useContents {
            with(LocalDensity.current) {
                size.height.toInt().toDp()
            }
        } ?: 0.dp
    }
}

actual fun getPlatform(): Platform = IOSPlatform()