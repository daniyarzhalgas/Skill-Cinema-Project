package sdu.project.cinemaapp.presentation.ui.bottomSheet

import android.util.Log
import androidx.collection.emptyObjectList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import sdu.project.cinemaapp.utills.defaultObjects.defaultMovie
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    private val rep: MoviesRepository
) : ViewModel() {

    private val _movie = MutableStateFlow<Movie>(defaultMovie)
    val movie = _movie.asStateFlow()

    private var isDataLoaded = false


    fun event(event: BottomSheetEvent){
        when(event){
            is BottomSheetEvent.UpdateWatchedStatus -> {
                updateWatchedStatus(event.updatedMovie)
            }
        }
    }

    private fun updateWatchedStatus(movie: Movie) {
        viewModelScope.launch {
            if (movie.isWatched)
                rep.setMovie(movie)
            else
                rep.deleteWatched(movie.kinopoiskId)
            _movie.update {  movie }
        }
    }

    fun loadMovieDetails(id : Int){
        if (isDataLoaded)
            return

        fetchBottomSheetData(id)
        isDataLoaded = true
    }

    private fun fetchBottomSheetData(id : Int){
        viewModelScope.launch {
            try {
                val movie = rep.getMovieById(id)
                _movie.value = movie
            }catch (e: Exception){
                Log.e("BottomSheetViewModel", "Exception ${e.cause}")
            }
        }
    }
}