package com.example.voyager_example.ui.tabs.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.navigator.Navigator
import com.example.voyager_example.base.BasicStatelessScreen
import com.example.voyager_example.vm.tabs.home.HomeDegreeViewModel

class HomeDegreeScreen : BasicStatelessScreen<HomeDegreeViewModel>(create = { get() }) {
    @Composable
    override fun modelContent(
        model: HomeDegreeViewModel,
        navigator: Navigator,
        tabbarHeight: Dp
    ) {

    }


}
