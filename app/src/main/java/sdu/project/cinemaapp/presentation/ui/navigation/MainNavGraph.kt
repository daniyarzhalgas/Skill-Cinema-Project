package sdu.project.cinemaapp.presentation.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager.BackStackEntry
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import sdu.project.cinemaapp.presentation.ui.screens.ProfileScreen
import sdu.project.cinemaapp.presentation.ui.screens.SearchScreen
import sdu.project.cinemaapp.presentation.ui.screens.HomeScreen
import sdu.project.cinemaapp.presentation.ui.screens.ListMoviesScreen
import sdu.project.cinemaapp.presentation.ui.screens.MovieDetailsScreen
import sdu.project.cinemaapp.presentation.viewModel.MovieViewModel


@Composable
fun MainNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarNavigation.Home.route
    ) {


        composable(BottomBarNavigation.Home.route) { backStackEntry ->

            val viewModel: MovieViewModel = hiltViewModel(backStackEntry)

            HomeScreen(
                onItemClick = { item ->
                    navController.navigate("details/${item.kinopoiskId}")
                },
                onClick = { title ->
                    navController.navigate("list_screen/${title}")
                },
                viewModel = viewModel
            )
        }


        composable("details/{itemId}") { entry ->

            val itemId = entry.arguments?.getString("itemId")?.toIntOrNull()

            itemId?.let {
                MovieDetailsScreen(itemId)
                Log.d("ItemCheck", "Item ID is valid: $itemId")
            }
        }


        composable("list_screen/{title}") { backStackEntry ->

            val title = backStackEntry.arguments?.getString("title")

            val viewModel: MovieViewModel =
                if (navController.previousBackStackEntry != null) {
                    hiltViewModel(backStackEntry)
                } else
                    hiltViewModel()

            title?.let {
                ListMoviesScreen(
                    title = title,
                    viewModel = viewModel,
                    onItemClick = { item -> navController.navigate("details/${item.kinopoiskId}")},
                    onClick = { navController.navigate(BottomBarNavigation.Home.route)}
                )
            }
        }

        composable(route = BottomBarNavigation.Search.route) {
            SearchScreen()
        }
        composable(route = BottomBarNavigation.Profile.route) {
            ProfileScreen()
        }
    }
}