package com.example.voyager_example.base

import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.ScreenModel
import org.koin.core.component.KoinComponent
import kotlin.jvm.Transient




abstract class BasicViewModel : ScreenModel, KoinComponent {
    open val topBarColor: Color
        get() = Color.White

    open val naviBarColor: Color
        get() = Color.White

    open val hasTopBar: Boolean
        get() = true

    open val title: String
        get() = "Basic Screen"

    @Transient
    internal val backPressed = derivedStateOf {
        { false }
    }

    open fun prepare() {}
    fun recycle() {

    }

     override fun onDispose() {
        println("navigator model this::${this::class.simpleName}")
    }

    final fun onBackPressed(): Boolean {
        return backPressed.value.invoke()
    }
}