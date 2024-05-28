package com.example.voyager_example.ui.tabs.study

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.example.voyager_example.base.BasicStatelessScreen
import com.example.voyager_example.vm.tabs.study.StudyMoocViewModel

class StudyMoocScreen : BasicStatelessScreen<StudyMoocViewModel>(create = { get() }) {

    @Composable
    override fun modelContent(
        model: StudyMoocViewModel,
        navigator: Navigator,
        tabbarHeight: Dp
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 15.dp, vertical = 20.dp)
        ) {

        }
    }
}