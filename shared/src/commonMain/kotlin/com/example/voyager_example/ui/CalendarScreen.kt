package com.example.voyager_example.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.navigator.Navigator
import com.example.voyager_example.base.BasicScreen
import com.example.voyager_example.vm.CalendarViewModel


class CalendarScreen : BasicScreen<CalendarViewModel>(create = { get() }) {
    @Composable
    override fun modelContent(
        model: CalendarViewModel,
        navigator: Navigator,
        tabbarHeight: Dp
    ) {
        val coroutine = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .padding(top = tabbarHeight)
                .fillMaxSize()
                .background(Color(0xFFF7F7F7)),
            verticalArrangement = Arrangement.Top
        ) {

        }
    }

}