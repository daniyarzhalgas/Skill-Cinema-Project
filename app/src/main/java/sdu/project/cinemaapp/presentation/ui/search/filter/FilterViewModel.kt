package sdu.project.cinemaapp.presentation.ui.search.filter

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import sdu.project.cinemaapp.presentation.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
) : ViewModel() {
    val countries = listOf("Россия", "Великобритания", "Германия", "США", "Франция")
    val genres = listOf("Комедия", "Мелодрама", "Боевик", "Вестерн", "Драма")


    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _country = MutableStateFlow( countries.first())
    val country = _country.asStateFlow()

    private val _genre = MutableStateFlow(genres.first())
    val genre = _genre.asStateFlow()

    private val _yearFrom = MutableStateFlow<Int>(1998)
    val yearFrom = _yearFrom.asStateFlow()

    private val _yearTo = MutableStateFlow<Int>(2009)
    val yearTo = _yearTo.asStateFlow()

    private val _visibleYears  = MutableStateFlow<List<Int>>(generateYears(1998, 12))
    val visibleYears = _visibleYears .asStateFlow()

    fun event(navController: NavHostController, event: FilterEvent) {
        when (event) {
            is FilterEvent.OnBackClicked -> {
                navController.popBackStack()
            }
            is FilterEvent.OnCountryClicked ->
                navController.navigate("filter_country")

            is FilterEvent.OnCountrySelected -> {
                _country.value = event.title
            }

            is FilterEvent.OnGenreSelected -> {
                _genre.value = event.genre
            }

            is FilterEvent.OnGenreClicked -> {
                navController.navigate("filter_genre")
            }

            is FilterEvent.OnYearClicked -> navController.navigate("filter_period")
            is FilterEvent.OnYearSelected -> {
                if (event.isYearFrom) _yearFrom.value = event.year else _yearTo.value = event.year
            }
            is FilterEvent.navigateYears -> {
                val firstYear = _visibleYears.value.first()
                val shift = if (event.backward) -12 else 12
                _visibleYears.value = generateYears(firstYear + shift, 12)
            }
        }
    }
}

private fun generateYears(startYear: Int, count: Int): List<Int> {
    return (startYear until startYear + count).toList()
}