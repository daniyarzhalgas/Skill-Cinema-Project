package sdu.project.cinemaapp.presentation.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.viewModel.SharedViewModel
import javax.inject.Inject

@HiltViewModel
class ListObjectsViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel
) : ViewModel() {
    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _movies = MutableStateFlow<List<Any>>(emptyList())
    val movies = _movies.asStateFlow()


    fun event(navController: NavController, event: ListObjectsEvent) {
        when (event) {
            is ListObjectsEvent.OnMovieClick -> {
                navController.navigate("details/${event.movieId}") {
                    launchSingleTop = true
                }
            }
            is ListObjectsEvent.OnActorClick -> {
                navController.navigate("actor_details/${event.staffId}") {
                    launchSingleTop = true
                }
            }
            is ListObjectsEvent.OnBackClick -> {
                navController.popBackStack()
            }
        }
    }

    init {
        showMovies()
    }

    private fun showMovies() {
        viewModelScope.launch {
            _state.update { ScreenState.Loading }
            try {
                val any = sharedViewModel.selectedDataList.value
                _movies.update { any }
                _state.update { ScreenState.Success }

            } catch (e: Exception) {
                _state.update { ScreenState.Error }
            }
        }
    }

}