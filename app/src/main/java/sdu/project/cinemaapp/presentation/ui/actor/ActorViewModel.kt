package sdu.project.cinemaapp.presentation.ui.actor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import sdu.project.cinemaapp.presentation.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private var isDataLoaded = false

    fun loadActorDetails(id: Int) {
        if (isDataLoaded) return

        fetchActorData(id)
        isDataLoaded = true
    }


    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _actor = MutableStateFlow<Actor?>(null)
    val actor = _actor.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    fun event(navController: NavHostController, event: ActorEvent) {
        when (event) {
            is ActorEvent.LoadActor -> fetchActorData(event.actorId)
            is ActorEvent.OnBackClick -> {
                navController.popBackStack()
            }
        }
    }


    private fun fetchActorData(id: Int) {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val actorDeferred = async { moviesRepository.getActor(id) }
                val actor = actorDeferred.await()

                val movies = actor.films
                    .take(10)
                    .map { film ->
                        async { moviesRepository.getMovieById(film.filmId) }
                    }.awaitAll()

                _actor.value = actor
                _movies.value = movies
                Log.i("ActorViewModel", "Movies: ${_movies.value}")
                _state.value = ScreenState.Success
            } catch (e: Exception) {
                Log.e("ActorViewModel", "Error: $e")
                _state.value = ScreenState.Error
            }
        }
    }

}