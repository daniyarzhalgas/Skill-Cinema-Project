package sdu.project.cinemaapp.presentation.ui.actor

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun ActorScreen(
    navController: NavHostController,
    viewModel: ActorViewModel = hiltViewModel()
) {

    TextButton(onClick = {viewModel.event(navController,   ActorEvent.OnBackClick)}) {
        Text(text = "Actor Screen")
    }
}