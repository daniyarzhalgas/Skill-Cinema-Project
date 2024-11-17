package sdu.project.cinemaapp.presentation.ui.actor

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.ui.screens.ErrorScreen
import sdu.project.cinemaapp.presentation.ui.screens.LoaderScreen

@Composable
fun ActorScreen(
    navController: NavHostController,
    actorId: Int,
    viewModel: ActorViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(actorId){
        viewModel.loadActorDetails(actorId)
    }

    val actor by viewModel.actor.collectAsStateWithLifecycle()


    when (state) {
        is ScreenState.Initial -> {}
        is ScreenState.Loading -> LoaderScreen()
        is ScreenState.Error -> ErrorScreen()
        is ScreenState.Success -> {
            actor?.let {
                Log.i("ActorScreen", "Getting Actor: $actor")
                TextButton(onClick = {viewModel.event(navController,   ActorEvent.OnBackClick)}) {
                    Text(text = "Actor Screen")
                }
            }
        }
    }

}