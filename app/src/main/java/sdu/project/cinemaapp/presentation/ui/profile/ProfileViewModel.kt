package sdu.project.cinemaapp.presentation.ui.profile

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
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val rep: MoviesRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _watched = MutableStateFlow<List<Movie>>(emptyList())
    val watched = _watched.asStateFlow()

    init {
        fetchProfileData()
    }

    private fun fetchProfileData() {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val movies = rep.getWatchedMovies()
                _watched.value = movies
                _state.value = ScreenState.Success
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "$e")
                _state.value = ScreenState.Error
            }
        }
    }

    fun event(event: ProfileEvent) {
            when(event){
                is ProfileEvent.DeleteAllWatchedMovies -> {
                    deleteAllWatchedMovies()
                }
            }
    }

    private fun deleteAllWatchedMovies(){
        viewModelScope.launch {
            rep.deleteAllWatchedMovies()
            fetchProfileData()
        }
    }



}