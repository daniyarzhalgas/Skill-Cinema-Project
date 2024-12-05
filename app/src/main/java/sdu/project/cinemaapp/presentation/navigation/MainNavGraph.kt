package sdu.project.cinemaapp.presentation.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import sdu.project.cinemaapp.presentation.navigation.BottomBarNavigation
import sdu.project.cinemaapp.presentation.ui.actor.ActorScreen
import sdu.project.cinemaapp.presentation.ui.profile.ProfileScreen
import sdu.project.cinemaapp.presentation.ui.search.mainPage.SearchScreen
import sdu.project.cinemaapp.presentation.ui.home.HomeScreen
import sdu.project.cinemaapp.presentation.ui.details.MovieDetailsScreen
import sdu.project.cinemaapp.presentation.ui.filmography.FilmographyScreen
import sdu.project.cinemaapp.presentation.ui.galleryPage.GalleryScreen
import sdu.project.cinemaapp.presentation.ui.list.ListObjectsScreen
import sdu.project.cinemaapp.presentation.ui.search.filter.components.CountryScreen
import sdu.project.cinemaapp.presentation.ui.search.filter.FilterScreen
import sdu.project.cinemaapp.presentation.ui.search.filter.FilterViewModel
import sdu.project.cinemaapp.presentation.ui.search.filter.components.GenreScreen
import sdu.project.cinemaapp.presentation.ui.search.filter.components.PeriodScreen


@Composable
fun MainNavGraph(
    navController: NavHostController,
    viewModel: FilterViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarNavigation.Home.route,
        route = "MainNavGraph"
    ) {

        composable(BottomBarNavigation.Home.route) {
            HomeScreen(navController = navController)
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
            SearchScreen(navController)

        }
        composable(route = BottomBarNavigation.Profile.route) {
            ProfileScreen(navController)
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
        composable("filter") {
            FilterScreen(navController, viewModel)
        }
        composable("filter_country"){
            CountryScreen(navController, viewModel)
        }
        composable("filter_genre") {
            GenreScreen(navController, viewModel)
        }
        composable("filter_period"){
            PeriodScreen(navController, viewModel)
        }
    }
}
/* TODO need to configure navigation and avoid duplicating screens {launchSingleTop = true} */