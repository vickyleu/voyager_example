package com.example.voyager_example.vm

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import com.example.voyager_example.base.BasicScreen
import com.example.voyager_example.base.BasicViewModel
import org.koin.core.component.inject
import com.example.voyager_example.ui.tabs.home.HomeCertificateScreen
import com.example.voyager_example.ui.tabs.home.HomeDegreeScreen
import com.example.voyager_example.ui.tabs.home.HomeMoocScreen
import com.example.voyager_example.ui.tabs.home.HomeRecommendScreen

class HomeViewModel : BasicViewModel() {
    val categoryList = mutableStateListOf<BasicScreen<*>>()
    val categorySelected = mutableIntStateOf(0)
    override val hasTopBar: Boolean
        get() = false
    val rootModel by inject<TabViewModel>()

    override fun prepare() {
        if (categoryList.isNotEmpty()) return
        categoryList.addAll(
            listOf(
                HomeRecommendScreen(),
                HomeMoocScreen(),
                HomeDegreeScreen(),
                HomeCertificateScreen()
            )
        )
    }
}

