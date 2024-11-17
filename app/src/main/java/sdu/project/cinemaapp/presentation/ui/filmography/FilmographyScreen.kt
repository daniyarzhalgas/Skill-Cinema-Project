package sdu.project.cinemaapp.presentation.ui.filmography

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import sdu.project.cinemaapp.presentation.ui.actor.ActorViewModel

@Composable
fun FilmographyScreen(
    navController: NavHostController,
    actorId: Int,
    viewModel: ActorViewModel = hiltViewModel()
) {

}