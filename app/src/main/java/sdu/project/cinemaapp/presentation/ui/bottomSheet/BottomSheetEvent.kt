package sdu.project.cinemaapp.presentation.ui.bottomSheet

import sdu.project.cinemaapp.domain.model.Movie

sealed interface BottomSheetEvent {
    data class UpdateWatchedStatus(val updatedMovie: Movie) : BottomSheetEvent
    data class UpdateCollectionStatus(val updatedMovie: Movie, val collectionTitle: String) : BottomSheetEvent
    data class CreateCollection(val title: String): BottomSheetEvent
    data class OnTextChange(val text: String): BottomSheetEvent
}