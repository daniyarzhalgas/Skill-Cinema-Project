package sdu.project.cinemaapp.presentation.ui.list

import sdu.project.cinemaapp.presentation.model.Event

sealed interface ListObjectsEvent: Event {
    data class OnActorClick(val staffId: Int) : ListObjectsEvent
    data class OnMovieClick(val movieId: Int) : ListObjectsEvent
    data object OnBackClick : ListObjectsEvent
}