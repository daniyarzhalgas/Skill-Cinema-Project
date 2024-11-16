package sdu.project.cinemaapp.presentation.ui.details

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import sdu.project.cinemaapp.domain.model.FilmStaff
import sdu.project.cinemaapp.domain.model.Image
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.SimilarMovie
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.ui.screens.ErrorScreen
import sdu.project.cinemaapp.presentation.ui.screens.LoaderScreen

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val getMovie by viewModel.movie.collectAsStateWithLifecycle()
    val getActors by viewModel.actors.collectAsStateWithLifecycle()
    val getStaff by viewModel.staff.collectAsStateWithLifecycle()
    val getImages by viewModel.images.collectAsStateWithLifecycle()
    val getSimilarFilms by viewModel.similarFilms.collectAsStateWithLifecycle()

    when (state) {
        is ScreenState.Initial -> {}
        is ScreenState.Loading -> LoaderScreen()
        is ScreenState.Success -> {
            MovieContent(
                getMovie,
                getActors,
                getStaff,
                getImages,
                getSimilarFilms
            )
            Log.i("MovieContent", "Getting Movie: ${getMovie.toString()}")
            Log.i("MovieContent", "Getting Actors: $getActors")
            Log.i("MovieContent", "Getting Staff: $getStaff")
            Log.i("MovieContent", "Getting Images: $getImages")
            Log.i("MovieContent", "Getting Similar Films: $getSimilarFilms")
        }

        is ScreenState.Error -> ErrorScreen()
    }

}

@Composable
fun MovieContent(
    movie: Movie?,
    actors: List<FilmStaff>,
    staff: List<FilmStaff>,
    images: List<Image>,
    similarFilms: List<SimilarMovie>
) {

    Column(
        modifier = Modifier
    ) {
        movie?.let {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
            ) {
                AsyncImage(
                    model = movie.posterUrl,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )


                Column(modifier = Modifier.align(Alignment.BottomCenter)) {

                    Text(
                        text = movie.description ?: "No description",
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
}