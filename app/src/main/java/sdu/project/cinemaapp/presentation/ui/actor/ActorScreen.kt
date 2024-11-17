package sdu.project.cinemaapp.presentation.ui.actor

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

@Composable
fun ActorScreen(
    navController: NavHostController,
    actorId: Int,
    viewModel: ActorViewModel = hiltViewModel()
) {
    LaunchedEffect(actorId){
        viewModel.event(navController, ActorEvent.LoadActor(actorId))
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val actor by viewModel.actor.collectAsStateWithLifecycle()

/*
*     when (state) {
        is ScreenState.Initial -> {}
        is ScreenState.Loading -> LoaderScreen()
        is ScreenState.Error -> ErrorScreen()
        is ScreenState.Success -> {
            here will be actor page`s ui, use actor to get actor info
        }

        *
        *

*
*
* */


    TextButton(onClick = {viewModel.event(navController,   ActorEvent.OnBackClick)}) {
        Text(text = "Actor Screen")
    }
}