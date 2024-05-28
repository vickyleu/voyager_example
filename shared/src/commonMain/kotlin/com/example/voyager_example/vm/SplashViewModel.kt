package com.example.voyager_example.vm

import com.example.voyager_example.base.BasicViewModel

class SplashViewModel : BasicViewModel() {
    override val title: String
        get() = "闪屏页"

    override val hasTopBar: Boolean
        get() = false

    override fun prepare() {

    }
}