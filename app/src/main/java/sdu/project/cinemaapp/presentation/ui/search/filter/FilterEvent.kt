package sdu.project.cinemaapp.presentation.ui.search.filter

import java.util.logging.Filter

sealed interface FilterEvent {
    data object OnBackClicked : FilterEvent
    data object OnCountryClicked : FilterEvent
    data class OnCountrySelected(val title: String) : FilterEvent
    data object OnGenreClicked : FilterEvent
    data class OnGenreSelected(val genre: String) : FilterEvent
    data object OnYearClicked : FilterEvent
    data class OnYearSelected(val isYearFrom: Boolean, val year: Int): FilterEvent
    data class navigateYears (val backward:Boolean): FilterEvent
}