package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.screens.HomeScreen
import com.example.myapplication.screens.ProfileScreen
import com.example.myapplication.screens.SearchScreen
import com.example.skill_cinema.ui.bottom.BottomBarScreen
import com.example.skill_cinema.ui.home.CinemaDetailsScreen

@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = BottomBarScreen.Home.route) {
        composable(route = BottomBarScreen.Home.route){
            HomeScreen(onItemClick = { item ->
                navController.navigate("details/${item.id}")
            })
        }
        composable("details/{itemId}") { entry ->
            val itemId = entry.arguments?.getString("itemId")?.toIntOrNull()
            if (itemId != null) {
                CinemaDetailsScreen(itemId)
            }
        }

        composable(route = BottomBarScreen.Search.route){
            SearchScreen()
        }
        composable(route = BottomBarScreen.Profile.route){
            ProfileScreen()
        }

    }
}
