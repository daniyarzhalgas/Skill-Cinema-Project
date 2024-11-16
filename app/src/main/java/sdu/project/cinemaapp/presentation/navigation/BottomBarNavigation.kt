package sdu.project.cinemaapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarNavigation (
    val route: String,
    val icon: ImageVector
){
    object Home: BottomBarNavigation(
        route = "home",
        icon = Icons.Default.Home
    )
    object Search: BottomBarNavigation(
        route = "search",
        icon = Icons.Default.Search
    )
    object Profile: BottomBarNavigation(
        route = "profile",
        icon = Icons.Default.Home
    )
}