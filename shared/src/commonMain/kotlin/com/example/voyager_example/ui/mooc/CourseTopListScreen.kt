package com.example.voyager_example.ui.mooc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import com.example.voyager_example.base.BasicScreen
import com.example.voyager_example.vm.mooc.CourseTopListViewModel
import org.jetbrains.compose.resources.painterResource
import voyager_example.shared.generated.resources.Res
import voyager_example.shared.generated.resources.ic_mine

class CourseTopListScreen :
    BasicScreen<CourseTopListViewModel>(create = { get() }) {
    @Composable
    override fun modelContent(
        model: CourseTopListViewModel,
        navigator: Navigator,
        tabbarHeight: Dp
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = tabbarHeight)
                .fillMaxSize()
                .background(Color.White)
        ) {
            items(model.courses.size) {
                CourseItemRow(model.courses[it])
            }
        }
    }

    @Composable
    private fun CourseItemRow(course: CourseTopListViewModel.CourseItem) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RankBadge(rank = course.rank)
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(Res.drawable.ic_mine),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = course.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${course.enrolled}人已学",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }

    @Composable
    private fun RankBadge(rank: Int) {
        val badgeColor = when (rank) {
            1 -> Color(0xFFFFD700) // Gold
            2 -> Color(0xFFC0C0C0) // Silver
            3 -> Color(0xFFCD7F32) // Bronze
            else -> Color.LightGray
        }

        Box(
            modifier = Modifier
                .size(24.dp)
                .background(badgeColor, shape = MaterialTheme.shapes.small),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rank.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }

}