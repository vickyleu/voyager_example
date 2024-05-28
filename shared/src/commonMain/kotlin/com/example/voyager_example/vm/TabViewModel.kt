package com.example.voyager_example.vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.voyager_example.base.BasicScreen
import com.example.voyager_example.base.BasicViewModel
import com.example.voyager_example.ui.tabs.HomeScreen
import com.example.voyager_example.ui.tabs.MineScreen
import com.example.voyager_example.ui.tabs.StudyScreen

class TabViewModel : BasicViewModel() {
    companion object {
        private enum class TabItem {
            Home, Mine
        }

        private val itemList = mapOf(
            TabItem.Home to HomeScreen::class,
            TabItem.Mine to MineScreen::class
        )
    }

    val items = mutableStateListOf<BasicScreen<*>>()

    var selected by mutableStateOf(0)
    override val hasTopBar: Boolean
        get() = false

    override fun prepare() {
        if (items.isNotEmpty()) return
        items.addAll(
            listOf(
                HomeScreen(0),
                StudyScreen(1),
                MineScreen(2)
            )
        )
    }
}