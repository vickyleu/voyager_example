package com.example.voyager_example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import com.example.voyager_example.ui.MyApplicationTheme
import com.example.voyager_example.ui.SplashScreen
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun App() {
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(Modifier.fillMaxSize().background(Color.Blue)) {
                val currentItem = remember { mutableStateOf<Screen>(SplashScreen()) }

                //    * https://issuetracker.google.com/issues/227926002
                ScaffoldDefaults.contentWindowInsets
                Navigator(
                    screen = currentItem.value,
                    disposeBehavior = NavigatorDisposeBehavior(
                        disposeNestedNavigators = false,
                        disposeSteps = false
                    ),
                    onBackPressed = { currentScreen ->
                        false
                    }
                )
                {
                    LaunchedEffect(Unit) {
                        snapshotFlow { it.lastItem }
                            .distinctUntilChanged()
                            .collect {
                                println("currentItem: $it")
                                currentItem.value = it
                            }
                    }
                    CompositionLocalProvider(LocalNavigatorController provides it) {
                        CurrentScreen()
                    }
                }
            }

        }
    }
}