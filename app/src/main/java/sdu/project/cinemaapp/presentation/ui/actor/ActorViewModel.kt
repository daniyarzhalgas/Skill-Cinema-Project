package sdu.project.cinemaapp.presentation.ui.actor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import sdu.project.cinemaapp.presentation.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _actor = MutableStateFlow<Actor?>(null)
    val actor = _actor.asStateFlow()

    fun event(navController: NavHostController, event: ActorEvent){
        when(event){
            is ActorEvent.OnBackClick -> {
                navController.popBackStack()
            }
        }
    }


    private fun fetchActorData(id: Int) {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                val actor = moviesRepository.getActor(id)
                _actor.value = actor
                _state.value = ScreenState.Success
            } catch (e: Exception) {
                _state.value = ScreenState.Error
            }
        }
    }

}