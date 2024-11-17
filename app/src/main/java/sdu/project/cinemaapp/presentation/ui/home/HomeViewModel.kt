package sdu.project.cinemaapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import sdu.project.cinemaapp.presentation.state.ScreenState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val currentDate = Date()
    private val monthFormat = SimpleDateFormat("MMMM", Locale.US)
    private val currentMonth = monthFormat.format(currentDate).uppercase(Locale.US) //month

    private val year = Date()
    private val yearFormat = SimpleDateFormat("yyyy", Locale.US)
    private val currentYear = yearFormat.format(year).uppercase(Locale.US)


    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private var _premieres = MutableStateFlow<List<Movie>>(emptyList())
    val premieres = _premieres.asStateFlow()

    private var _comicsCollections = MutableStateFlow<List<Movie>>(emptyList())
    val comicsCollections = _comicsCollections.asStateFlow()

    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies = _popularMovies.asStateFlow()


    init {
        fetchAllHomeData()
    }

    fun event(navController: NavHostController, event: HomeEvent) {
        when (event) {
            is HomeEvent.OnItemClick -> {
                navController.navigate("details/${event.movieId}") {
                    launchSingleTop = true
                }
            }

            is HomeEvent.OnClick -> {
                navController.navigate("list_screen/${event.string}")
            }
        }
    }


    private fun fetchAllHomeData() {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {

                val premieresDeferred = async { moviesRepository.getPremieres(currentMonth, currentYear, null) }
                val comicsCollectionsDeferred = async { moviesRepository.getPopular("COMICS_THEME", null) }
                val popularMoviesDeferred = async { moviesRepository.getPopular("TOP_POPULAR_MOVIES", null) }

                _premieres.value = premieresDeferred.await()
                _comicsCollections.value = comicsCollectionsDeferred.await()
                _popularMovies.value = popularMoviesDeferred.await()

                _state.value = ScreenState.Success

            } catch (e: Exception) {
                _state.value = ScreenState.Error
            }
        }
    }
}

/*
TODO
 handle the possible exceptions :
    1) The URL or URI used in the API is incorrect.
    2) The server is unavailable, and the app could not connect to it.
    3) A network latency issue.
    4) Poor or no internet connection on the device.
*/