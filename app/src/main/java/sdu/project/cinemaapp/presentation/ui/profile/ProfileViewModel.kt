package sdu.project.cinemaapp.presentation.ui.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.MovieCollection
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.viewModel.SharedViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val rep: MoviesRepository,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _watched = MutableStateFlow<List<Movie>>(emptyList())
    val watched = _watched.asStateFlow()

    private val _collectionCount = MutableStateFlow<Map<String, Int>>(emptyMap())
    val collectionCount = _collectionCount.asStateFlow()

    var collectionTitle by mutableStateOf("")
        private set

    val movieCollection: StateFlow<List<MovieCollection>> = rep.getCollections()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = emptyList()
        )

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

    fun event(navController: NavHostController, event: ProfileEvent) {
        when (event) {
            is ProfileEvent.DeleteAllWatchedMovies -> {
                deleteAllWatchedMovies()
            }

            is ProfileEvent.NavigateToMovie -> {
                navController.navigate(
                    "details/${event.id}"
                ) {
                    launchSingleTop
                }
            }

            is ProfileEvent.NavigateToListPage -> {
                navController.navigate("list_screen/${event.title}")
            }

            is ProfileEvent.NavigateToCollection -> {
                viewModelScope.launch {
                    navigateToCollection(event.title)
                    navController.navigate("list_screen/${event.title}")
                }
            }

            is ProfileEvent.OnTextChange -> {
                collectionTitle = event.title
            }

            is ProfileEvent.CreateCollection -> {
                createCollection(event.title)
                collectionTitle = ""
            }
        }
    }

    private fun createCollection(title: String) {
        viewModelScope.launch {
            val collection = rep.getCollection(title)

            if (collection == null) {
                rep.upsertCollection(MovieCollection(title))
            }
            else
                rep.upsertCollection(collection)
        }
    }

    fun getCollectionCount(title: String) {
        viewModelScope.launch {
            rep.getCollectionCount(title).collect { count ->
                _collectionCount.update { currentCount ->
                    currentCount + (title to count)
                }
            }
        }
    }

    private suspend fun navigateToCollection(title: String) {
        val movies = rep.getMoviesByCollection(title)

        sharedViewModel.setDataList(movies)
    }


    private fun deleteAllWatchedMovies() {
        viewModelScope.launch {
            rep.deleteAllWatchedMovies()
            fetchProfileData()
        }
    }


}