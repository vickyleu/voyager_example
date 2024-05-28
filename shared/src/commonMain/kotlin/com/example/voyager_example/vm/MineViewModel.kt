package com.example.voyager_example.vm

import com.example.voyager_example.base.BasicViewModel
import org.koin.core.component.inject

class MineViewModel : BasicViewModel() {
    val rootModel by inject<TabViewModel>()

    override val hasTopBar: Boolean
        get() = false
}