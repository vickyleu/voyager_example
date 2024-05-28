package com.example.voyager_example.ui.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.voyager_example.base.BasicTab
import com.example.voyager_example.vm.MineViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import voyager_example.shared.generated.resources.Res
import voyager_example.shared.generated.resources.ic_mine
import voyager_example.shared.generated.resources.ic_mine_selected
import voyager_example.shared.generated.resources.mine_tab

class MineScreen(private val index: Int) : BasicTab<MineViewModel>(create = { get() }) {

    @Composable
    override fun modelOption(model: MineViewModel, tabNavigator: Navigator): TabOptions {
        var isSelected by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            snapshotFlow { model.rootModel.selected }
                .distinctUntilChanged()
                .map {
                    it == index
                }
                .distinctUntilChanged()
                .collect {
                    isSelected = it
                }
        }
        return TabOptions(
            index = index.toUShort(), title = stringResource(Res.string.mine_tab),
            icon = painterResource(if (isSelected) Res.drawable.ic_mine_selected else Res.drawable.ic_mine)
        )
    }

    @Composable
    override fun modelContent(model: MineViewModel, navigator: Navigator, tabbarHeight: Dp) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(top = tabbarHeight)
                .fillMaxHeight()
        ) {
            Text("我的")
        }
    }
}