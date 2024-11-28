package sdu.project.cinemaapp.presentation.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sdu.project.cinemaapp.data.local.AppDatabase
import sdu.project.cinemaapp.domain.model.FilmStaff
import sdu.project.cinemaapp.domain.model.MovieImage
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.SimilarMovie
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import sdu.project.cinemaapp.presentation.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private var isDataLoaded = false

    fun loadMovieDetails(id: Int) {
        if (isDataLoaded) return

        fetchAllMovieDetails(id)
        isDataLoaded = true
    }

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    private val _actors = MutableStateFlow<List<FilmStaff>>(emptyList())
    val actors = _actors.asStateFlow()

    private val _staff = MutableStateFlow<List<FilmStaff>>(emptyList())
    val staff = _staff.asStateFlow()

    private val _images = MutableStateFlow<List<MovieImage>>(emptyList())
    val images = _images.asStateFlow()

    private val _similarFilms = MutableStateFlow<List<SimilarMovie>>(emptyList())
    val similarFilms = _similarFilms.asStateFlow()


    fun event(navController: NavHostController, event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.LoadMovie -> fetchAllMovieDetails(event.movieId)

            is MovieDetailsEvent.LoadStaff -> {
                navController.navigate("actor_details/${event.staffId}") {
                    launchSingleTop = true
                }
            }

            is MovieDetailsEvent.OnBackClick -> {
                navController.popBackStack()
            }

            is MovieDetailsEvent.LoadGallery -> {
                navController.navigate("gallery_screen")
            }

            is MovieDetailsEvent.NavigateToList -> {
                navController.navigate("list_screen/${event.title}")
            }

        }
    }


    private fun fetchAllMovieDetails(id: Int) {
        viewModelScope.launch {
            _state.value = ScreenState.Loading

            try {
                val movie = async { moviesRepository.getMovieById(id) }
                val actorsDeferred = async { moviesRepository.getActors(id) }
                val staffDeferred = async { moviesRepository.getStaff(id) }
                val imagesDeferred = async { moviesRepository.getImages(id) }
                val similarMoviesDeferred = async { moviesRepository.getSimilarMovies(id) }

                _movie.value = movie.await()
                _actors.value = actorsDeferred.await()
                _staff.value = staffDeferred.await()
                _images.value = imagesDeferred.await()
                _similarFilms.value = similarMoviesDeferred.await()

                _state.value = ScreenState.Success

            } catch (e: Exception) {
                Log.e("MovieDetailsViewModel", "Failed to fetch movie details", e)
                _state.value = ScreenState.Error
            }
        }
    }
}