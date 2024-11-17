package sdu.project.cinemaapp.presentation.ui.filmography

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.viewModel.SharedViewModel
import javax.inject.Inject

@HiltViewModel
class FilmographyViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _filmography = MutableStateFlow<List<Movie>>(emptyList())
    val filmography = _filmography.asStateFlow()

    private val actor: Actor = sharedViewModel.getDataOfType<Actor>()

    init {
        // Загрузка фильмов по первой доступной профессии
        actor.films.firstOrNull()?.professionKey?.let {
            loadMoviesByProfessionKey(it)
        }
    }

    fun event(navController: NavController, event: FilmographyEvent) {
        when (event) {
            is FilmographyEvent.OnBackClick -> {
                navController.popBackStack()
            }

            is FilmographyEvent.LoadMovieByProfessionKey -> {
                loadMoviesByProfessionKey(event.professionKey)
            }
            is FilmographyEvent.OnMovieClick -> {
                navController.navigate("details/${event.movieId}")
            }
        }
    }

    private fun loadMoviesByProfessionKey(professionKey: String) {
        _state.value = ScreenState.Loading
        viewModelScope.launch {
            try {
                // Фильтруем фильмы по профессии
                val moviesByProfession = actor.films.filter {
                    it.professionKey == professionKey
                } ?: emptyList()

                // Получаем список фильмов из sharedViewModel
                val allMovies = sharedViewModel.selectedMovies.value

                // Фильтруем фильмы по ID
                val filteredMovies = allMovies.filter { movie ->
                    movie.kinopoiskId in moviesByProfession.map { it.filmId }
                }

                _filmography.value = filteredMovies
                _state.value = ScreenState.Success
            } catch (e: Exception) {
                Log.e("FilmographyViewModel", "Error: ${e.message}")
                _state.value = ScreenState.Error
            }
        }
    }
}