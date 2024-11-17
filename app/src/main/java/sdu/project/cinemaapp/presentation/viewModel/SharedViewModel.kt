package sdu.project.cinemaapp.presentation.viewModel

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

    private val _selectedDataList = MutableStateFlow<List<Any>>(emptyList())
    val selectedDataList = _selectedDataList.asStateFlow()

    private val _selectedData = MutableStateFlow<Any?>(null)
    val selectedData = _selectedData.asStateFlow()

    fun setMovies(movies: List<Movie>) {
        _selectedMovies.update { movies }
    }

    fun setDataList(anyData: List<Any>) {
        _selectedDataList.value = anyData
    }

    inline fun <reified T> getDataListOfType(): List<T> {
        return selectedDataList.value.filterIsInstance<T>()
    }

    fun setData(any: Any){
        _selectedData.value = any
    }

    inline fun <reified T> getDataOfType(): T {
        return selectedData.value.let { it as? T } ?: throw IllegalArgumentException("Invalid data type")
    }

}