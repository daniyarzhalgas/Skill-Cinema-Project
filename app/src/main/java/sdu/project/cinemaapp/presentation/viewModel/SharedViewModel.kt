package sdu.project.cinemaapp.presentation.viewModel

import android.media.Image
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import sdu.project.cinemaapp.domain.model.Movie
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _selectedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val selectedMovies = _selectedMovies.asStateFlow()
    private val _selectedActorId = MutableStateFlow<Int?>(null)
    val selectedActorId = _selectedActorId.asStateFlow()

    private val _selectedData = MutableStateFlow<List<Any>>(emptyList())
    val selectedData = _selectedData.asStateFlow()

    fun setMovies(movies: List<Movie>) {
        _selectedMovies.update { movies }
    }

    fun setData(anyData: List<Any>) {
        _selectedData.value = anyData
    }

    inline fun <reified T> getDataOfType(): List<T> {
        return selectedData.value.filterIsInstance<T>()
    }

}