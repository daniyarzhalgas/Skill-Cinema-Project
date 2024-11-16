package sdu.project.cinemaapp.presentation.ui.movieList

import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.presentation.model.Event

sealed interface ListMoviesEvent: Event {

    data class OnItemClick(val movie: Movie) : ListMoviesEvent
    data object OnBackClick : ListMoviesEvent
}