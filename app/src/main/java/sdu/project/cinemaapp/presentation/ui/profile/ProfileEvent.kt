package sdu.project.cinemaapp.presentation.ui.profile

sealed interface ProfileEvent {
    data object DeleteAllWatchedMovies: ProfileEvent
}