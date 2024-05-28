package com.example.voyager_example.vm.mooc

import androidx.compose.runtime.mutableStateListOf
import com.example.voyager_example.base.BasicViewModel

class CourseTopListViewModel : BasicViewModel() {
    val courses = mutableStateListOf<CourseItem>()

    override val title: String
        get() = "课程Top榜"

    override fun prepare() {
        if (courses.isNotEmpty()) return
        courses.addAll(
            listOf(
                CourseItem("形式与政策", 9000000, 1),
                CourseItem("形式与政策", 9000000, 2),
                CourseItem("形式与政策", 9000000, 3),
                CourseItem("形式与政策", 9000000, 4),
                CourseItem("形式与政策", 9000000, 5),
                CourseItem("形式与政策", 9000020, 6),
                CourseItem("形式与政策", 7900000, 7)
            )
        )
    }

    data class CourseItem(
        val title: String,
        val enrolled: Int,
        val rank: Int
    )
}