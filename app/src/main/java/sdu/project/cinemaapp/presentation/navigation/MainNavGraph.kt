package sdu.project.cinemaapp.presentation.ui.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import sdu.project.cinemaapp.presentation.navigation.BottomBarNavigation
import sdu.project.cinemaapp.presentation.ui.actor.ActorScreen
import sdu.project.cinemaapp.presentation.ui.components.BottomBar
import sdu.project.cinemaapp.presentation.ui.profile.ProfileScreen
import sdu.project.cinemaapp.presentation.ui.screens.SearchScreen
import sdu.project.cinemaapp.presentation.ui.home.HomeScreen
import sdu.project.cinemaapp.presentation.ui.details.MovieDetailsScreen
import sdu.project.cinemaapp.presentation.ui.filmography.FilmographyScreen
import sdu.project.cinemaapp.presentation.ui.galleryPage.GalleryScreen
import sdu.project.cinemaapp.presentation.ui.list.ListObjectsScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarNavigation.Home.route
    ) {

        composable(BottomBarNavigation.Home.route) {
            Scaffold(
                bottomBar = { BottomBar(navController) }
            ) {
                HomeScreen(navController = navController)
            }
        }

        composable("details/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            id?.let {
                MovieDetailsScreen(navController, id)
            }
        }


        composable("list_screen/{title}") { backStackEntry ->

            val title = backStackEntry.arguments?.getString("title")

            title?.let { ListObjectsScreen(title = title, navController) }
        }

        composable(route = BottomBarNavigation.Search.route) {
            Scaffold(
                bottomBar = { BottomBar(navController) }
            ) {
                SearchScreen()
            }
        }
        composable(route = BottomBarNavigation.Profile.route) {
            Scaffold(
                bottomBar = { BottomBar(navController) }
            ) {
                ProfileScreen()
            }
        }
        composable("actor_details/{id}") { backStackEntry ->
            val actorId = backStackEntry.arguments?.getString("id")?.toIntOrNull()

            actorId?.let {
                Log.i("ActorId", "ActorId: $actorId")
                ActorScreen(navController, actorId)
            }
        }
        composable("filmography") {
                FilmographyScreen(navController)
        }
        composable("gallery_screen") {
            GalleryScreen(navController)
        }
    }
}
/* TODO need to configure navigation and avoid duplicating screens {launchSingleTop = true} */