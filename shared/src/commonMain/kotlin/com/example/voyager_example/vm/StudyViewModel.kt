package com.example.voyager_example.vm

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import com.example.voyager_example.base.BasicScreen
import com.example.voyager_example.base.BasicViewModel
import com.example.voyager_example.ui.tabs.study.StudyCertificateScreen
import com.example.voyager_example.ui.tabs.study.StudyDegreeScreen
import com.example.voyager_example.ui.tabs.study.StudyMoocScreen
import org.koin.core.component.inject

class StudyViewModel : BasicViewModel() {
    val categoryList = mutableStateListOf<BasicScreen<*>>()
    val categorySelected = mutableIntStateOf(0)
    val rootModel by inject<TabViewModel>()

    override val hasTopBar: Boolean
        get() = false

    override fun prepare() {
        if (categoryList.isNotEmpty()) return
        categoryList.addAll(
            listOf(
                StudyMoocScreen(),
                StudyDegreeScreen(),
                StudyCertificateScreen()
            )
        )
    }
}