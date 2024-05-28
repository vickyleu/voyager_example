package com.example.voyager_example.ui.tabs.study

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import cafe.adriel.voyager.navigator.Navigator
import com.example.voyager_example.base.BasicStatelessScreen
import com.example.voyager_example.ripple
import com.example.voyager_example.ui.CalendarScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import com.example.voyager_example.ui.tabs.LocalScrollController
import com.example.voyager_example.vm.tabs.study.StudyDegreeViewModel
import voyager_example.shared.generated.resources.Res
import voyager_example.shared.generated.resources.ic_mine

/**
 * 学历提升
 */
class StudyDegreeScreen : BasicStatelessScreen<StudyDegreeViewModel>(create = { get() }) {
    @Composable
    override fun modelContent(
        model: StudyDegreeViewModel,
        navigator: Navigator,
        tabbarHeight: Dp
    ) {
        val scrollConnection = LocalScrollController.current

        LazyColumn(Modifier.nestedScroll(scrollConnection)) {
            item {
                Box(
                    modifier = Modifier
                        .padding(
                            top = WindowInsets.statusBars.asPaddingValues()
                                .calculateTopPadding() + 49.dp + 20.dp * 2
                        )
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 15.dp, vertical = 20.dp)
                ) {
                    TodaySchedule(navigator)
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 15.dp, vertical = 20.dp)
                ) {
                    MyCourses()
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 15.dp, vertical = 20.dp)
                ) {
                    ExerciseOptions()
                }
            }
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }

    @Composable
    private fun ExerciseOptions() {
        Column {
            Text(text = "刷题冲刺", style = MaterialTheme.typography.titleLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ExerciseOption("章节练习", Res.drawable.ic_mine)
                ExerciseOption("历年真题", Res.drawable.ic_mine)
                ExerciseOption("模拟题", Res.drawable.ic_mine)
                ExerciseOption("冲刺题", Res.drawable.ic_mine)
//                ExerciseOption("章节练习", Res.drawable.ic_chapter_practice)
//                ExerciseOption("历年真题", Res.drawable.ic_past_exams)
//                ExerciseOption("模拟题", Res.drawable.ic_mock_exams)
//                ExerciseOption("冲刺题", Res.drawable.ic_cram_exams)
            }
        }
    }

    @Composable
    private fun MyCourses() {
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "我的课程", style = MaterialTheme.typography.titleLarge)
                TextButton(onClick = { /* TODO: navigate to more courses */ }) {
                    Text(text = "专业专业")
                }
            }
            val courses = remember {
                listOf(
                    CourseInfo("形式与政策形式与政策形式形式形式形式", 33),
                    CourseInfo("形式与政策形式与政策形式形式形式形式", 33)
                )
            }
            Column {
                courses.fastForEach {
                    CourseItem(it)
                }
            }
        }
    }

    @Composable
    private fun TodaySchedule(navigator: Navigator) {
        Column(modifier = Modifier.padding(bottom = 16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(text = "今日课表", style = MaterialTheme.typography.titleLarge)
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                val interactionSource = remember { MutableInteractionSource() }
                val isPressed by interactionSource.collectIsPressedAsState()
                val backgroundAlpha = animateFloatAsState(
                    targetValue = if (isPressed) 0.2f else 0.6f, label = "backgroundAlpha"
                ).value
                val scope = rememberCoroutineScope()
                Image(
                    modifier = Modifier.size(30.dp)
                        .clickable(
                            interactionSource = interactionSource, indication = ripple(
                                color = Color.Blue.copy(alpha = backgroundAlpha),
                            )
                        ) {
                            try {
                                navigator.push(CalendarScreen())
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        },
                    painter = painterResource(Res.drawable.ic_mine),
                    contentDescription = null
                )
            }
            val classes = remember {
                listOf(
                    ClassInfo(
                        "毛泽东思想和中国特色社会主义理论体系概论",
                        "精讲01",
                        "陈老师",
                        "9:30-10:00",
                        "已下课"
                    ),
                    ClassInfo(
                        "毛泽东思想和一行效果一行效果一行效果",
                        "",
                        "陈老师",
                        "9:30-10:00",
                        "即将开始"
                    ),
                    ClassInfo(
                        "毛泽东思想和一行效果一行效果一行效果",
                        "",
                        "陈老师",
                        "9:30-10:00",
                        "上课中"
                    )
                )
            }
            Column {
                classes.fastForEach {
                    ClassItem(it)
                }
            }
        }
    }


    @Composable
    private fun ClassItem(classInfo: ClassInfo) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            // Image would be implemented here
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${classInfo.name} ${classInfo.extra}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Row {
                    Text(text = classInfo.teacher, style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = classInfo.time, style = MaterialTheme.typography.bodySmall)
                }
                Text(
                    text = classInfo.status,
                    color = when (classInfo.status) {
                        "已下课" -> Color.Gray
                        "即将开始" -> Color.Red
                        "上课中" -> Color.Blue
                        else -> Color.Black
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

    @Composable
    private fun CourseItem(course: CourseInfo) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_mine),
//                painter = painterResource( Res.drawable.ic_course_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = course.name, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "上课进度 ${course.progress}%",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

    @Composable
    private fun ExerciseOption(name: String, icon: DrawableResource) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(text = name, fontSize = 12.sp)
        }
    }

    data class ClassInfo(
        val name: String,
        val extra: String,
        val teacher: String,
        val time: String,
        val status: String
    )

    data class CourseInfo(
        val name: String,
        val progress: Int
    )
}