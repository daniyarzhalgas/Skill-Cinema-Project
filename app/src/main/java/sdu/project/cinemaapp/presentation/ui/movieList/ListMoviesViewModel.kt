package sdu.project.cinemaapp.presentation.ui.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.viewModel.SharedViewModel
import javax.inject.Inject

@HiltViewModel
class ListMoviesViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel
) : ViewModel() {
    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()


    fun event(navController: NavController, event: ListMoviesEvent) {
        when (event) {
            is ListMoviesEvent.OnItemClick -> {
                navController.navigate("details/${event.movieId}") {
                    launchSingleTop = true
                }
            }
            is ListMoviesEvent.OnBackClick -> {
                navController.popBackStack()
            }
        }
    }
    init {
        showMovies()
    }

    private fun showMovies() {
        viewModelScope.launch {
            _state.update { ScreenState.Loading }
            try {
                sharedViewModel.selectedMovies.collect { movies ->
                    _movies.update {movies}
                    _state.update { ScreenState.Success }
                }
            } catch (e: Exception) {
                _state.update { ScreenState.Error }
            }
        }
    }

}