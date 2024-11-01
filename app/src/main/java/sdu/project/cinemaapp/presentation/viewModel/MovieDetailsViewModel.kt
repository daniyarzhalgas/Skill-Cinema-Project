package sdu.project.cinemaapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import sdu.project.cinemaapp.presentation.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel(){

    private val _state =  MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _getMovie = MutableStateFlow<List<Movie>>(emptyList())
    val getMovie = _getMovie.asStateFlow()

    fun getMovieId(id: Int) {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try{
                val movie = moviesRepository.getMovieById(id)
                _getMovie.value = listOf(movie)
                _state.value = ScreenState.Success
            }
            catch(e: Exception){
                _state.value = ScreenState.Error
            }
        }
    }
}