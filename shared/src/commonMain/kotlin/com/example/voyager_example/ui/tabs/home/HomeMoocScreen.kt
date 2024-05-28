package com.example.voyager_example.ui.tabs.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import cafe.adriel.voyager.navigator.Navigator
import com.example.voyager_example.base.BasicStatelessScreen
import com.example.voyager_example.lightenColor
import com.example.voyager_example.ui.mooc.CourseTopListScreen
import com.example.voyager_example.vm.tabs.home.HomeMoocViewModel
import org.jetbrains.compose.resources.painterResource
import voyager_example.shared.generated.resources.Res
import voyager_example.shared.generated.resources.ic_mine
import voyager_example.shared.generated.resources.ic_right_more
import kotlin.jvm.Transient

class HomeMoocScreen : BasicStatelessScreen<HomeMoocViewModel>(create = { get() }) {
    @Composable
    override fun modelContent(
        model: HomeMoocViewModel,
        navigator: Navigator,
        tabbarHeight: Dp
    ) {

        val elevateHeight =
            91.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 26.dp
        LazyColumn(
            modifier = Modifier
                .padding(top = elevateHeight)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                HotRankingSection(navigator)
            }
            item {
                CourseRecommendationSection(courses = sampleCourses)
            }
            item {
                FeaturedCoursesSection(courses = mutableListOf<CourseInfo>().apply {
                    addAll(sampleCourses)
                    addAll(sampleCourses)
                    addAll(sampleCourses)
                })
            }
            item {
                CourseRecommendationBannerSection(
                    courses = sampleBannerCourses
                )
            }


            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }

    @Composable
    private fun FeaturedCoursesSection(courses: List<CourseInfo>) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp)
                .background(Color(0xFFFFFFFF), RoundedCornerShape(12.dp))
                .padding(bottom = 20.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 15.dp, top = 20.dp, bottom = 20.dp),
                text = "精选好课",
                style = MaterialTheme.typography.titleLarge
            )
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                LazyRow(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        alignment = Alignment.Start
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (courses.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                    items(courses.size) {
                        val courseInfo = courses[it]
                        FeaturedCourseItem(courseInfo, this@BoxWithConstraints.maxWidth)
                    }
                    if (courses.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }
            }


        }
    }

    @Composable
    fun FeaturedCourseItem(course: CourseInfo, maxWidth: Dp) {
        Column(
            modifier = Modifier
                .width(150.dp)
                .wrapContentHeight()
                .background(
                    Color(0xFFF8FAFB),
                    RoundedCornerShape(6.dp)
                )
                .clip(RoundedCornerShape(6.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Replace with actual image resource
            Image(
                painter = painterResource(Res.drawable.ic_mine),
                contentDescription = "banner",
                modifier = Modifier
                    .width(150.dp)
                    .aspectRatio(150f / 103f)
                    .background(Color.Black, RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                    .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
            )
            Column(
                modifier = Modifier.wrapContentHeight()
                    .padding(start = 12.dp, end = 12.dp, top = 10.dp, bottom = 12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    maxLines = 2,
                    minLines = 2,
                    fontSize = 15.sp, color = Color(0xFF333333),
                    fontWeight = FontWeight.Medium,
                    text = course.name, style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "宁波大学",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF91969F),
                    fontSize = 12.sp
                )
                Text(
                    text = "王雷(副教授)",
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF91969F),
                    fontSize = 12.sp
                )
            }
        }
    }

    @Composable
    private fun HotRankingSection(navigator: Navigator) {
        BoxWithConstraints {
            Column(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(Color(0xFF0B6FEB), Color(0xFF9EDBFF)),
                            Offset(0f, this.maxWidth.value),
                            Offset(this.maxHeight.value, 0f),
                        ), RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "热门排行榜",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 17.sp,
                    )
                    TextButton(
                        onClick = {
                            navigator.push(CourseTopListScreen())
                        },
                        contentPadding = PaddingValues(top = 20.dp, bottom = 18.dp, end = 20.dp)
                    ) {
                        Text(text = "更多", color = Color.White, fontSize = 12.sp)
                        Image(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(12.dp),
                            painter = painterResource(Res.drawable.ic_right_more),
                            contentDescription = "more"
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            color = Color.White, RoundedCornerShape(12.dp)
                        ).padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val hotCourses = remember {
                        listOf(
                            CourseInfo(
                                "形式与政策形式与政策形式形式形式与政策形式与政策形式形式形式与政策形式与政策形式形式形式与政策形式与政策形式形式",
                                "90w人已学"
                            ),
                            CourseInfo("公共关系与人际交际一行行行", "90w人已学"),
                            CourseInfo("公共关系与人际交往", "90w人已学")
                        )
                    }
                    hotCourses.forEach { course ->
                        HotCourseItem(course)
                    }
                }


            }
        }
    }

    @Composable
    private fun HotCourseItem(course: CourseInfo) {
        Row(
            modifier = Modifier
                .padding(start = 11.dp, end = 15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Image(
                    painter = painterResource(Res.drawable.ic_mine), // 请替换为实际图片资源
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 4.dp, start = 4.dp, bottom = 4.dp)
                        .size(86.dp, 59.dp)
                        .background(Color.Black, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(8.dp))
                )
                Image(
                    painter = painterResource(Res.drawable.ic_mine), // 请替换为实际图片资源
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color.Red),
                    modifier = Modifier
                        .size(18.dp, 28.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
//                .padding(top = 3.dp)
            ) {
                Text(
                    text = course.name,
                    color = Color(0xFF333333),
                    fontSize = 15.sp, fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 2
                )
                Text(
                    text = course.participants,
                    color = Color(0xFF91969F),
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

    @Composable
    private fun CourseRecommendationSection(
        courses: List<CourseInfo>,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))

        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "学分课推荐",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    fontSize = 17.sp,
                    color = Color(0xFF333333)
                )
                TextButton(
                    onClick = { /* TODO: More */ },
                    contentPadding = PaddingValues(top = 20.dp, bottom = 18.dp, end = 15.dp)
                ) {
                    Text(text = "更多", color = Color(0xFF91969F), fontSize = 12.sp)
                    Image(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(12.dp),
                        colorFilter = ColorFilter.tint(Color(0xFF91969F)),
                        painter = painterResource(Res.drawable.ic_right_more),
                        contentDescription = "more"
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp, top = 2.dp),
                verticalArrangement = Arrangement
                    .spacedBy(10.dp)
            ) {
                courses.fastForEachIndexed { index, courseInfo ->
                    if (index > 0 && index < courses.size) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp),
                            thickness = 1.dp,
                            color = Color(0xFFF7F7F7)
                        )
                    }
                    CourseRecommendationItem(courseInfo)
                }
            }
        }
    }

    @Composable
    private fun CourseRecommendationBannerSection(
        courses: List<CourseBannerInfo>
    ) {
        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))

        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 15.dp)
                    .padding(top = 20.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "学分课推荐",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    fontSize = 17.sp,
                    color = Color(0xFF333333)
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp, top = 2.dp),
                verticalArrangement = Arrangement
                    .spacedBy(10.dp)
            ) {
                courses.fastForEachIndexed { index, bannerInfo ->
                    if (index > 0 && index < courses.size) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp),
                            thickness = 1.dp,
                            color = Color(0xFFF7F7F7)
                        )
                    }
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 16.dp, end = 20.dp, bottom = 10.dp)
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.ic_mine),
                            contentDescription = "banner",
                            modifier = Modifier.fillMaxWidth()
                                .aspectRatio(315f / 217f)
//                                .background(Color.Red, RoundedCornerShape(6.dp))
                                .background(Color.Black, RoundedCornerShape(6.dp))
                                .clip(RoundedCornerShape(6.dp))
                        )
                        Box(
                            modifier = Modifier.align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .aspectRatio(315f / 66f)
                                .background(
                                    lightenColor(Color(0xFF000000).copy(alpha = 0.25f), 0.6f),
                                    RoundedCornerShape(bottomEnd = 6.dp, bottomStart = 6.dp)
                                )
                                .padding(horizontal = 15.dp, vertical = 12.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    "形式与政策形式与政策形式与一行一行一",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        "宁波大学/王蕾( 副教授)",
                                        color = Color.White.copy(alpha = 0.7f),
                                        fontSize = 12.sp,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        "20326人参加",
                                        color = Color.White.copy(alpha = 0.7f),
                                        fontSize = 12.sp,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    bannerInfo.courseInfo.fastForEachIndexed { i, info ->
                        if (i > 0 && i < bannerInfo.courseInfo.size) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp),
                                thickness = 1.dp,
                                color = Color(0xFFF7F7F7)
                            )
                        }
                        CourseRecommendationItem(info)
                    }
                }
            }
        }
    }

    @Composable
    private fun CourseRecommendationItem(course: CourseInfo) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_mine), // 请替换为实际图片资源
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp, 82.dp)
                    .background(Color.Black, RoundedCornerShape(8.dp))
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = course.name, style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF333333),
                    fontSize = 15.sp,
                    maxLines = 2
                )
                Text(
                    text = "宁波大学/王雷(副教授)",
                    modifier = Modifier.padding(top = 4.dp),
                    color = Color(0xFF91969F),
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .background(Color(0xFFF8FAFB), RoundedCornerShape(2.dp))
                        .padding(vertical = 1.dp, horizontal = 4.dp),
                    text = course.participants,
                    color = Color(0xFF91969F),
                    fontSize = 10.sp,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }


}

data class CourseInfo(
    val name: String,
    val participants: String
)

data class CourseBannerInfo(
    val name: String,
    val participants: String,
    val courseInfo: List<CourseInfo>
)

@Transient
// 示例课程数据
val sampleCourses = listOf(
    CourseInfo("形式与政策形式与政策形式形式形式", "20326人参加"),
    CourseInfo("形式与政策形式与政策形式形式", "20326人参加"),
    CourseInfo("形式与政策形式与政策形式形式", "20326人参加")
)

@Transient
val sampleBannerCourses = listOf(
    CourseBannerInfo("形式与政策形式与政策形式形式形式", "20326人参加", sampleCourses),
    CourseBannerInfo("形式与政策形式与政策形式形式", "20326人参加", sampleCourses),
    CourseBannerInfo("形式与政策形式与政策形式形式", "20326人参加", sampleCourses)
)