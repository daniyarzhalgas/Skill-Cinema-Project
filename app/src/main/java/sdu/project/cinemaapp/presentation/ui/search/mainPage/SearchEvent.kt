package sdu.project.cinemaapp.presentation.ui.search.mainPage

sealed interface SearchEvent {
    data object OnBackClicked : SearchEvent
    data object OnFilterClick : SearchEvent
    data class OnSearchTextChange(val text: String) : SearchEvent
    data class OnMovieClick(val movieId: Int) : SearchEvent


    data object OnCountryClicked : SearchEvent
    data class OnCountrySelected(val title: String) : SearchEvent
    data object OnGenreClicked : SearchEvent
    data class OnGenreSelected(val genre: String) : SearchEvent
    data object OnYearClicked : SearchEvent
    data class OnYearSelected(val isYearFrom: Boolean, val year: Int) : SearchEvent
    data class NavigateYears(val isYearFrom: Boolean, val backward: Boolean) : SearchEvent
    data class OnSliderValueChange(val newSliderValue: ClosedFloatingPointRange<Float>) :
        SearchEvent

    data class OnTabsSelected(val isFirst: Boolean, val tab: String): SearchEvent
    data object OnFilterUsed : SearchEvent

}