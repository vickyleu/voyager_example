package com.example.voyager_example.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.navigator.Navigator
import com.example.voyager_example.ripple
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopbar(
    model: BasicViewModel,
    navigator: Navigator,
    onBack: (() -> Unit)? = null,
    topAppBarHeightAssign: MutableState<Dp>,
) {

    val canPopFunc = remember {
        {
            if (navigator.canPop) {
                onBack?.invoke()
            }
        }
    }
    LaunchedEffect(Unit) {
        snapshotFlow { model.backPressed.value }
            .distinctUntilChanged()
            .collect {
                canPopFunc.invoke()
            }
    }
    with(LocalDensity.current) {
        if (model.hasTopBar) {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .background(model.topBarColor)
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .aspectRatio(10.5f)
                    .onSizeChanged { size ->
                        topAppBarHeightAssign.value = size.height.toDp()
                    },//.windowInsetsTopHeight(),
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    titleContentColor = Color.Black,
                ),
                navigationIcon = {
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()
                    val backgroundAlpha = animateFloatAsState(
                        targetValue = if (isPressed) 0.2f else 0.6f, label = "backgroundAlpha"
                    ).value
                    AnimatedVisibility(visible = navigator.canPop) {
                        Icon(
                            modifier = Modifier.clickable(
                                interactionSource = interactionSource,
                                indication = ripple(
                                    color = Color.Blue.copy(alpha = backgroundAlpha),
                                )
                            ) {
                                canPopFunc.invoke()
                            },
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                title = {
                    Text(model.title)
                },
            )
        }
    }
}