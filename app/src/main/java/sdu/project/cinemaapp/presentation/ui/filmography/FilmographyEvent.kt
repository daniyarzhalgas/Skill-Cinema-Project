package sdu.project.cinemaapp.presentation.ui.filmography

interface FilmographyEvent {
    data object OnBackClick : FilmographyEvent
    data class LoadMovieByProfessionKey(val professionKey: String) : FilmographyEvent
    data class OnMovieClick(val movieId: Int): FilmographyEvent
}