package sdu.project.cinemaapp.presentation.ui.search.filter

sealed interface FilterEvent {
    data object OnBackClicked : FilterEvent
    data object OnCountryClicked : FilterEvent
    data class OnCountrySelected(val title: String) : FilterEvent
    data object OnGenreClicked : FilterEvent
    data class OnGenreSelected(val genre: String) : FilterEvent
    data object OnYearClicked : FilterEvent
    data class OnYearSelected(val isYearFrom: Boolean, val year: Int) : FilterEvent
    data class NavigateYears(val isYearFrom: Boolean, val backward: Boolean) : FilterEvent
    data class OnSliderValueChange(val newSliderValue: ClosedFloatingPointRange<Float>) :
        FilterEvent

    data class OnTabsSelected(val isFirst: Boolean, val tab: String): FilterEvent
    data object OnFilterUsed : FilterEvent
}