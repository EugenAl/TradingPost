package dpr.svich.tradingpost.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import dpr.svich.tradingpost.Router

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Профиль",
            image = Icons.Filled.Person,
            route = Router.PROFILE_SCREEN
        ),
        BarItem(
            title = "Главная",
            image = Icons.Filled.Star,
            route = Router.MAIN_SCREEN
        ),
        BarItem(
            title = "Аналитика",
            image = Icons.Filled.DateRange,
            route = Router.ANALYTICS_SCREEN
        )
    )
}