package sdu.project.cinemaapp.presentation.ui.home

import sdu.project.cinemaapp.presentation.model.Event

interface HomeEvent: Event{
    data class OnItemClick(val movieId: Int) : HomeEvent
    data class OnClick(val string: String) : HomeEvent
}