package sdu.project.cinemaapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
class MovieViewModel @Inject constructor(
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
        getPremieres()
        getComicsCollections()
        getPopularMovies()
    }


    private fun getPremieres() {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val movies = moviesRepository.getPremieres(currentMonth, currentYear, null)
                if (movies.isNotEmpty()) {
                    _premieres.value = movies
                    _state.value = ScreenState.Success
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading premieres", e)
                _state.value = ScreenState.Error
            }
        }
    }

    private fun getComicsCollections() {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val movies = moviesRepository.getPopular("COMICS_THEME", null)
                _comicsCollections.value = movies
                _state.value = ScreenState.Success
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading comics", e)
                _state.value = ScreenState.Error
            }
        }
    }

    private fun getPopularMovies(){
        viewModelScope.launch {
            try {
                val movies = moviesRepository.getPopular("TOP_POPULAR_MOVIES", null)
                _popularMovies.value = movies
                _state.value = ScreenState.Success
            }catch (e: Exception){
                Log.e("HomeViewModel", "Error loading popular movies", e)
                _state.value = ScreenState.Error
            }
        }
    }
}