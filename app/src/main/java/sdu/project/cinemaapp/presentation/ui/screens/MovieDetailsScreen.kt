package sdu.project.cinemaapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.presentation.state.ScreenState

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val getMovie by viewModel.getMovie.collectAsStateWithLifecycle()

    when (state) {
        is ScreenState.Initial -> {}
        is ScreenState.Loading -> LoaderScreen()
        is ScreenState.Success -> {
            MovieContent(getMovie[0])
            Log.d("MovieContent", getMovie[0].toString())
        }

        is ScreenState.Error -> ErrorScreen()
    }
    LaunchedEffect(movieId) {
        viewModel.getMovieId(movieId)
    }

}

@Composable
fun MovieContent(movie: Movie) {

    Column(
        modifier = Modifier
    ) {
        Box (modifier = Modifier.fillMaxSize().padding(bottom = 80.dp)){
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )


            Column(modifier = Modifier.align(Alignment.BottomCenter)) {

                Text(
                    text = movie.description?: "No description",
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = movie.genres.joinToString(", ") { it.genre }
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = movie.ratingKinopoisk.toString() + " " + movie.kinopoiskId.toString()
                )
            }
        }
    }
}