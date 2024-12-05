package sdu.project.cinemaapp.presentation.ui.search.mainPage

sealed interface SearchEvent {
    data object OnBackClicked : SearchEvent
    data object OnFilterClick : SearchEvent
    data class OnSearchTextChange(val text: String) : SearchEvent
    data class OnMovieClick(val movieId: Int) : SearchEvent
}