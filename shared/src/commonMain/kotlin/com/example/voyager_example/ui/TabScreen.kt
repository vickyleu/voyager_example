package com.example.voyager_example.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.voyager_example.base.BasicScreen
import com.example.voyager_example.base.BasicViewModel
import com.example.voyager_example.getPlatform
import com.example.voyager_example.vm.TabViewModel
import kotlinx.coroutines.flow.distinctUntilChanged


class TabScreen : BasicScreen<TabViewModel>(create = { get() }) {
    override fun prepare(model: TabViewModel) {
        super.prepare(model)
    }

    @Composable
    override fun modelContent(model: TabViewModel, navigator: Navigator, tabbarHeight: Dp) {
        var selectedItem by remember { mutableStateOf(model.items[model.selected]) }
        LaunchedEffect(Unit) {
            snapshotFlow { model.selected }
                .distinctUntilChanged()
                .collect {
                    selectedItem = model.items[it]
                    println("selectedItem: $selectedItem")
                }
        }
        Scaffold(
            content = {
                Box(
                    modifier = Modifier
                        .padding(bottom = getPlatform().getNavigationBarHeight() + 49.dp)
                        .fillMaxWidth()
                ) {
                    TabNavigator(selectedItem)
                }
            },
            bottomBar = {
                // [link: https://medium.com/mobile-innovation-network/navigation-in-compose-multiplatform-with-animations-6e8d1cac840b ]
                AppBottomNavigationBar(model, show = true) {
                    model.items.fastForEachIndexed { index, basicScreen ->
                        TabNavigationItem(
                            tab = basicScreen,
                            isSelected = model.selected == index,
                        ) {
                            model.selected = index
                        }
                    }
                }
            }
        )

    }
}

@Composable
fun AppBottomNavigationBar(
    mode: BasicViewModel,
    modifier: Modifier = Modifier,
    show: Boolean,
    content: @Composable (RowScope.() -> Unit),
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        modifier = modifier//.windowInsetsPadding(BottomAppBarDefaults.windowInsets)
    ) {
        if (show) {
            Column {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.6.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(mode.naviBarColor)
                        .padding(bottom = getPlatform().getNavigationBarHeight())
                        .height(49.dp)
                        .selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}


@Composable
private fun RowScope.TabNavigationItem(
    modifier: Modifier = Modifier, tab: BasicScreen<*>,
    isSelected: Boolean,
    onClick: () -> Unit = { },

    ) {
    Column(
        modifier = modifier
            .weight(1f)
            .clickable(
                onClick = {
                    onClick()
                },
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val icon = tab.options.icon!!
        Image(
            painter = icon,
            contentDescription = icon.toString(),
            contentScale = ContentScale.Crop,
            /*  colorFilter = if (selected) {
                  ColorFilter.tint(MaterialTheme.colorScheme.primary)
              } else {
                  ColorFilter.tint(MaterialTheme.colorScheme.outline)
              },*/
            modifier = modifier.then(
                Modifier.clickable {
                    onClick()
                }
                    .size(24.dp)
            )
        )

        Text(
            text = tab.options.title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) {
                FontWeight.SemiBold
            } else {
                FontWeight.Normal
            },
            fontSize = 12.sp,
            color = if (isSelected) {
                Color(0xFF333333)
            } else {
                Color(0xFF91969F)
            }
//            color = if (selected) {
//                MaterialTheme.colorScheme.primary
//            } else {
//                MaterialTheme.colorScheme.outline
//            }
        )
    }
}