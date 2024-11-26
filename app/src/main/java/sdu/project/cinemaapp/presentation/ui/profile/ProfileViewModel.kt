package sdu.project.cinemaapp.presentation.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sdu.project.cinemaapp.data.local.AppDatabase
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.WatchedMovie
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import sdu.project.cinemaapp.presentation.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val db: AppDatabase,
    private val rep: MoviesRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _watched = MutableStateFlow<List<Movie>>(emptyList())
    val watched = _watched.asStateFlow()

    init {
        fetchProfileData()
    }


    fun event(event: ProfileInterface){

    }

    private fun fetchProfileData() {
        viewModelScope.launch {
            val watchedd = WatchedMovie(1,2323)
            db.watchedMovieDao().setWatchedMovie(watchedd)
            db.movieDao().setMovie(rep.getMovieById(watchedd.movieId))
            Log.i("ProfileViewModel", "${db.movieDao().getMovie(watchedd.movieId)}")

            _state.value = ScreenState.Loading
            try {
                val watched = db.watchedMovieDao().getAllWatchedMovies()

                val movie = db.movieDao().getMovies(watched.map { movie -> movie.movieId })

                _watched.value = movie
                _state.value = ScreenState.Success
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "$e")
                _state.value = ScreenState.Error
            }
        }
    }

}