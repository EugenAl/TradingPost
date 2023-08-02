package dpr.svich.tradingpost

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dpr.svich.tradingpost.navigation.NavBarItems
import dpr.svich.tradingpost.screen.Analytics
import dpr.svich.tradingpost.screen.Greeting
import dpr.svich.tradingpost.screen.MainView
import dpr.svich.tradingpost.screen.Profile
import dpr.svich.tradingpost.ui.theme.TradingPostTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TradingPostTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    val isGreeting = remember {
                        mutableStateOf(false)
                    }
                    if (isGreeting.value) {
                        Greeting(name = "Kitty")
                    } else {
                        Scaffold(
                            content = { contentPadding ->
                                NavHost(
                                    navController = navController,
                                    startDestination = Router.MAIN_SCREEN
                                ) {
                                    composable(Router.PROFILE_SCREEN) {
                                        Profile(contentPadding)
                                    }
                                    composable(Router.MAIN_SCREEN) {
                                        MainView(contentPadding)
                                    }
                                    composable(Router.ANALYTICS_SCREEN) {
                                        Analytics(contentPadding)
                                    }
                                }
                            },
                            bottomBar = {
                                val currentScreen = remember {
                                    mutableStateOf(Router.MAIN_SCREEN)
                                }
                                NavigationBar(Modifier.fillMaxWidth()) {
                                    NavBarItems.BarItems.forEach { navItem ->
                                        NavigationBarItem(
                                            selected = navItem.route == currentScreen.value,
                                            onClick = {
                                                currentScreen.value = navItem.route
                                                navController.navigate(navItem.route) {
                                                    popUpTo(navController.graph
                                                        .findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            },

                                            icon = {
                                                Icon(
                                                    imageVector = navItem.image,
                                                    contentDescription = navItem.title
                                                )
                                            },
                                            label = {
                                                Text(text = navItem.title)
                                            })

                                    }

                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview() {
    TradingPostTheme {
    }
}