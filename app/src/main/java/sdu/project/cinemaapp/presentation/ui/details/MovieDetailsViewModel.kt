package sdu.project.cinemaapp.presentation.ui.details

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.domain.model.FilmStaff
import sdu.project.cinemaapp.domain.model.Image
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.SimilarMovie
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.viewModel.SharedViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _movie = MutableStateFlow<List<Movie>>(emptyList())
    val movie = _movie.asStateFlow()

    private val _actors = MutableStateFlow<List<FilmStaff>>(emptyList())
    val actors = _actors.asStateFlow()

    private val _staff = MutableStateFlow<List<FilmStaff>>(emptyList())
    val staff = _actors.asStateFlow()

    private val _actor = MutableStateFlow<Actor?>(null)
    val actor = _actor.asStateFlow()

    private val _images = MutableStateFlow<List<Image>>(emptyList())
    val images = _images.asStateFlow()

    private val _similarFilms = MutableStateFlow<List<SimilarMovie>>(emptyList())
    val similarFilms = _similarFilms.asStateFlow()

    fun getMovieId(id: Int) {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val movie = moviesRepository.getMovieById(id)
                _movie.value = listOf(movie)
                _state.value = ScreenState.Success
            } catch (e: Exception) {
                _state.value = ScreenState.Error
            }
        }
    }

    fun getActors(id: Int) {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val actors = moviesRepository.getActors(id)
                _actors.value = actors
                _state.value = ScreenState.Success
            } catch (e: Exception) {
                _state.value = ScreenState.Error
            }
        }
    }

    fun getStaff(id: Int) {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val staff = moviesRepository.getStaff(id)
                _staff.value = staff
                _state.value = ScreenState.Success
            }catch (e: Exception){
                _state.value = ScreenState.Error
            }            }
    }

    fun getActor(id: Int){
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val actor = moviesRepository.getActor(id)
                _actor.value = actor
                _state.value = ScreenState.Success
            }catch (e: Exception){
                _state.value = ScreenState.Error
            }
        }
    }
    fun getImages(id: Int) {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val images = moviesRepository.getImages(id)
                _images.value = images
                _state.value = ScreenState.Success
            } catch (e: Exception) {
                _state.value = ScreenState.Error
            }
        }
    }

    fun getSimilarFilms(id: Int){
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val similarFilms = moviesRepository.getSimilarMovies(id)
                _similarFilms.value = similarFilms
                _state.value = ScreenState.Success
            } catch (e: Exception){
                _state.value = ScreenState.Error
            }
        }
    }
}