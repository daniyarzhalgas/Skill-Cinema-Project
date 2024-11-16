package sdu.project.cinemaapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import sdu.project.cinemaapp.domain.model.Movie
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _selectedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val selectedMovies = _selectedMovies.asStateFlow()

    fun setMovies(movies: List<Movie>) {
        Log.i("SharedViewModel", "Setting movies: $movies")
        _selectedMovies.value = movies
    }

    fun setMovie(movie: Movie){
        Log.i("SharedViewModel", "Setting movie: $movie")
        _selectedMovies.value = listOf(movie)
    }
}