package sdu.project.cinemaapp.presentation.ui.galleryPage

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.ui.details.ListImages
import sdu.project.cinemaapp.presentation.ui.screens.ErrorScreen
import sdu.project.cinemaapp.presentation.ui.screens.LoaderScreen
import sdu.project.cinemaapp.presentation.viewModel.SharedViewModel

@Composable
fun GalleryScreen(
    navController: NavHostController,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val images by viewModel.gallery.collectAsStateWithLifecycle()

    when (state) {
        is ScreenState.Initial -> {}
        is ScreenState.Loading -> LoaderScreen()
        is ScreenState.Success -> {
            Log.i("GalleryScreen", "Images: $images")
            ListImages( images)
        }
        is ScreenState.Error -> ErrorScreen()
    }
}