package com.example.voyager_example.ui.tabs.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.navigator.Navigator
import com.example.voyager_example.base.BasicStatelessScreen
import com.example.voyager_example.vm.tabs.home.HomeRecommendViewModel

class HomeRecommendScreen : BasicStatelessScreen<HomeRecommendViewModel>(create = { get() }) {
    @Composable
    override fun modelContent(
        model: HomeRecommendViewModel,
        navigator: Navigator,
        tabbarHeight: Dp
    ) {
        Text("Home Recommend Screen")
    }
}