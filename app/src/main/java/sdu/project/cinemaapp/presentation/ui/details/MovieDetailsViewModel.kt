package sdu.project.cinemaapp.presentation.ui.details

import android.util.Log
import androidx.collection.emptyObjectList
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
    private val moviesRepository: MoviesRepository,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _movie = MutableStateFlow<Movie?>(null)
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

    init {
        getMovie()
        getActors()
        getActor()
        getStaff()
        getImages()
        getSimilarFilms()
    }

    private fun getMovie() {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val movie = sharedViewModel.selectedMovies.value[0]
                _movie.value = movie
                _state.value = ScreenState.Success
            } catch (e: Exception) {
                _state.value = ScreenState.Error
            }
        }
    }

    private fun getActors() {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val filmId = sharedViewModel.selectedMovies.value[0].kinopoiskId
                val actors = moviesRepository.getActors(filmId)
                _actors.value = actors
                _state.value = ScreenState.Success
            } catch (e: Exception) {
                _state.value = ScreenState.Error
            }
        }
    }

    private fun getStaff() {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val filmId = sharedViewModel.selectedMovies.value[0].kinopoiskId
                val staff = moviesRepository.getStaff(filmId)
                _staff.value = staff
                _state.value = ScreenState.Success
            }catch (e: Exception){
                _state.value = ScreenState.Error
            }            }
    }

    private fun getActor(){
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val filmId = sharedViewModel.selectedMovies.value[0].kinopoiskId
                val actor = moviesRepository.getActor(filmId)
                _actor.value = actor
                _state.value = ScreenState.Success
            }catch (e: Exception){
                _state.value = ScreenState.Error
            }
        }
    }
    private fun getImages() {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val filmId = sharedViewModel.selectedMovies.value[0].kinopoiskId
                val images = moviesRepository.getImages(filmId)
                _images.value = images
                _state.value = ScreenState.Success
            } catch (e: Exception) {
                _state.value = ScreenState.Error
            }
        }
    }

    private fun getSimilarFilms(){
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val filmId = sharedViewModel.selectedMovies.value[0].kinopoiskId
                val similarFilms = moviesRepository.getSimilarMovies(filmId)
                Log.i("MovieDetailsViewModel", "Getting similar films $filmId")
                Log.i("MovieDetailsViewModel", "Getting similar films $similarFilms")
                _similarFilms.value = similarFilms
                _state.value = ScreenState.Success
            } catch (e: Exception){
                _state.value = ScreenState.Error
            }
        }
    }
}