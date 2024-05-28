package com.example.voyager_example.ui

import com.example.voyager_example.base.BasicScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.navigator.Navigator
import com.example.voyager_example.vm.SplashViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SplashScreen : BasicScreen<SplashViewModel>(create = { get() }) {
    @Composable
    override fun modelContent(
        model: SplashViewModel,
        navigator: Navigator,
        tabbarHeight: Dp
    ) {
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                delay(1000 * 1)
                withContext(Dispatchers.Main) {
                    navigator.replace(TabScreen())
                    this.cancel()
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize().background(Color.Red))
    }
}