package sdu.project.cinemaapp.presentation.ui.bottomSheet

import sdu.project.cinemaapp.domain.model.Movie

sealed interface BottomSheetEvent {
    data class UpdateWatchedStatus(val updatedMovie: Movie) : BottomSheetEvent
}