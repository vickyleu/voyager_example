package com.example.voyager_example.base


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.voyager_example.LocalNavigatorController
import com.example.voyager_example.statusBarHeight
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import kotlin.jvm.Transient

expect interface BasicScreenSerializer

abstract class BasicScreen<T : BasicViewModel>(
    @Transient private val create: Koin.() -> T
) : Tab, KoinComponent, BasicScreenSerializer {

    @delegate:Transient
    internal val model: T by lazy {
        create(getKoin())
    }

    open val rootColor: Color
        @Composable
        get() = MaterialTheme.colorScheme.background


    @Suppress("UNCHECKED_CAST")
    @Composable
    final override fun Content() {
        val screenModel: BasicViewModel =rememberScreenModel<BasicViewModel>(tag ="${model::class.qualifiedName}"){
            model
        }
        DisposableEffect(Unit) {
            prepare(screenModel as T)
            onDispose {
                println("navigator dispose ${this@BasicScreen::class.simpleName}")
                recycle()
            }
        }
        with(LocalDensity.current) {

            val topAppBarHeightAssign = remember { mutableStateOf(0.dp) }
            Scaffold(modifier = Modifier.fillMaxSize(), containerColor = rootColor,
                topBar = {
                    modelTopBar(screenModel as T, navigator, topAppBarHeightAssign)
                },
                content = {
                    val tabbarHeight =
                        WindowInsets.statusBars.statusBarHeight + topAppBarHeightAssign.value
                    BoxWithConstraints(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                    ) {
                        modelContent(screenModel as T, navigator, tabbarHeight)
                    }
                },
                bottomBar = {
                    modelBottomBar(screenModel as T, navigator)
                }
            )
        }
    }

    open fun prepare(model: T) {
        model.prepare()
    }


    override val options: TabOptions
        @Composable
        get() = TabOptions(index = 0u, title = "${this::class.simpleName}")

    @Composable
    open fun modelTopBar(
        model: T,
        navigator: Navigator,
        topAppBarHeightAssign: MutableState<Dp>
    ) {
        BasicTopbar(
            onBack = {
                if (navigator.canPop) {
                    navigator.pop()
                }
            },
            model = model,
            navigator = navigator,
            topAppBarHeightAssign = topAppBarHeightAssign
        )
    }

    @Composable
    abstract fun modelContent(model: T, navigator: Navigator, tabbarHeight: Dp)

    @Composable
    open fun modelBottomBar(model: T, navigator: Navigator) {
    }

    @Composable
    open fun canPop(): Boolean {
        return navigator.canPop
    }

    fun recycle() {
        model.recycle()
    }

    fun onBackPressed(): Boolean {
        return model.onBackPressed()
    }
}


abstract class BasicStatelessScreen<T : BasicViewModel>(@Transient val create: Koin.() -> T) :
    BasicScreen<T>(create) {
    @Composable
    final override fun modelBottomBar(model: T, navigator: Navigator) {
    }

    @Composable
    final override fun modelTopBar(
        model: T,
        navigator: Navigator,
        topAppBarHeightAssign: MutableState<Dp>
    ) {
    }

    final override val options: TabOptions
        @Composable
        get() = super.options

    override val rootColor: Color
        @Composable
        get() = Color.Transparent

    @Composable
    final override fun canPop(): Boolean {
        return super.canPop()
    }
}

abstract class BasicTab<T : BasicViewModel>(@Transient val create: Koin.() -> T) :
    BasicScreen<T>(create) {
    @Suppress("UNCHECKED_CAST")
    override val options: TabOptions
        @Composable
        get() {
            val screenModel = rememberScreenModel<ScreenModel>(tag = "tab=${model::class.qualifiedName}") { model }
            return modelOption(screenModel as T, tabNavigator)
        }

    @Composable
    final override fun modelBottomBar(model: T, navigator: Navigator) {
    }

    /*@Composable
    override fun Content() {
        val tabNavigator = LocalTabNavigator.current
        val modelEntry = model
        tabNavigator.current.rememberScreenModel<BasicViewModel>(tag="tab:${modelEntry::class.simpleName}${this.hashCode()}") { modelEntry }
        super.Content()
    }*/
    @Composable
    final override fun modelTopBar(
        model: T,
        navigator: Navigator,
        topAppBarHeightAssign: MutableState<Dp>
    ) {
    }


    @Composable
    abstract fun modelOption(model: T, tabNavigator: Navigator): TabOptions

    @Composable
    final override fun canPop(): Boolean {
        return super.canPop()
    }
}

private val BasicScreen<*>.navigator: Navigator
    @Composable
    get() = LocalNavigatorController.current


private val BasicScreen<*>.tabNavigator: Navigator
    @Composable
    get() = LocalNavigator.current!!


//private val BasicScreen<*>.navigator: NavHostController
//    @Composable
//    get() = LocalNavigatorController.current!!