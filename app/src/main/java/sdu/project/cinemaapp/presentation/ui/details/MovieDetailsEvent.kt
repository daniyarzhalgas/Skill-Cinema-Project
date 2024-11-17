package sdu.project.cinemaapp.presentation.ui.details

import sdu.project.cinemaapp.presentation.model.Event
import sdu.project.cinemaapp.presentation.ui.actor.ActorEvent

interface MovieDetailsEvent: Event {
    data class LoadMovie(val movieId: Int): MovieDetailsEvent
    data class LoadStaff(val staffId: Int): MovieDetailsEvent
    data object OnBackClick : MovieDetailsEvent
    data object LoadGallery: MovieDetailsEvent

}