package sdu.project.cinemaapp.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import sdu.project.cinemaapp.presentation.ui.navigation.MainNavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    MainNavGraph(navController)
}