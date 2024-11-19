package sdu.project.cinemaapp.presentation.ui.actor

interface ActorEvent {
    data object OnBackClick : ActorEvent
    data class LoadActor(val actorId: Int) : ActorEvent
    data object OnFilmographyClick : ActorEvent
    data class OnMovieClick(val movieId: Int) : ActorEvent
    data class NavigateToList(val title: String): ActorEvent
}