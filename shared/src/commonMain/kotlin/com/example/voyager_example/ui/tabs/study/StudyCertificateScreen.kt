package com.example.voyager_example.ui.tabs.study

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.navigator.Navigator
import com.example.voyager_example.base.BasicStatelessScreen
import com.example.voyager_example.vm.tabs.study.StudyCertificateViewModel

class StudyCertificateScreen: BasicStatelessScreen<StudyCertificateViewModel>(create = { get() }) {
    @Composable
    override fun modelContent(model: StudyCertificateViewModel, navigator: Navigator, tabbarHeight: Dp) {
    }
}