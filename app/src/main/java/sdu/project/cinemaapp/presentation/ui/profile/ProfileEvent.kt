package sdu.project.cinemaapp.presentation.ui.profile

sealed interface ProfileEvent {
    data object DeleteAllWatchedMovies : ProfileEvent
    data class NavigateToMovie(val id: Int) : ProfileEvent
    data class NavigateToListPage(val title: String) : ProfileEvent
    data class NavigateToCollection(val title: String) : ProfileEvent
}