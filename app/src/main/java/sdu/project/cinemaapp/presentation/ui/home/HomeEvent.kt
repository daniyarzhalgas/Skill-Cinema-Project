package sdu.project.cinemaapp.presentation.ui.home

import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.presentation.model.Event

interface HomeEvent: Event{
    data class OnItemClick(val movie: Movie) : HomeEvent
    data class OnClick(val string: String) : HomeEvent
}