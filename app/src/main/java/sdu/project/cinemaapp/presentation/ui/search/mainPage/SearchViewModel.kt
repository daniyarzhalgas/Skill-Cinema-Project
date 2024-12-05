package sdu.project.cinemaapp.presentation.ui.search.mainPage

import android.util.Log
import android.util.Printer
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import sdu.project.cinemaapp.presentation.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {


    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _searchText = MutableStateFlow<String>("")
    val searchText = _searchText.asStateFlow()


    fun event(navController: NavHostController, event: SearchEvent) {
        when (event) {
            is SearchEvent.OnFilterClick -> {
                navController.navigate("filter")
            }

            is SearchEvent.OnBackClicked -> navController.popBackStack()

            is SearchEvent.OnSearchTextChange -> {
                _searchText.update { event.text }
            }

            is SearchEvent.OnMovieClick -> {
                navController.navigate("details/${event.movieId}")
            }
        }
    }

    init {
        observeSearchTextChanges()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchTextChanges() {
        viewModelScope.launch {
            _searchText
                .debounce(500)
                .onEach { text ->
                    Log.d("SearchText", "Current input: $text")
                }
                .filter { text ->
                    Log.d("FilterCheck", "Checking text: $text")
                    text.isNotBlank()
                }
                .collectLatest { input ->
                    Log.d("CollectInput", "Fetching movies for input: $input")
                    fetchSearchingMovies(input)
                }
        }
    }

    private suspend fun fetchSearchingMovies(query: String) {
        try {
            _state.value = ScreenState.Loading
            val moviesResult = moviesRepository.searchByKeyword(query)
            _movies.value = moviesResult
            _state.value = ScreenState.Success
        } catch (e: Exception) {
            Log.e("SearchViewModel", "Error: $e")
            _state.value = ScreenState.Error
        }
    }

}