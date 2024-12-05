package sdu.project.cinemaapp.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import sdu.project.cinemaapp.presentation.ui.components.BottomBar
import sdu.project.cinemaapp.presentation.ui.navigation.MainNavGraph
import sdu.project.cinemaapp.presentation.ui.search.filter.FilterViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) {
        MainNavGraph(navController)
    }
}