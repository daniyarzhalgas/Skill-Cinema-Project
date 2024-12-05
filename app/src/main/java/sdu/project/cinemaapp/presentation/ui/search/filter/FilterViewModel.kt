package sdu.project.cinemaapp.presentation.ui.search.filter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import sdu.project.cinemaapp.presentation.state.ScreenState
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {

    val countries = listOf("Россия", "Великобритания", "Германия", "США", "Франция")
    val genres = listOf("Комедия", "Мелодрама", "Боевик", "Вестерн", "Драма")

    val tabs = listOf("Все", "Фильмы", "Сериалы")
    val tabs2 = listOf("Дата", "Популярность", "Рейтинг")

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

    private val _visibleYearsFirst  = MutableStateFlow<List<Int>>(generateYears(1998, 12))
    val visibleYearsFirst = _visibleYearsFirst .asStateFlow()

    private val _visibleYearsSecond  = MutableStateFlow<List<Int>>(generateYears(1998, 12))
    val visibleYearsSecond = _visibleYearsSecond .asStateFlow()

    private val _sliderValues = MutableStateFlow(1f .. 10f)
    val sliderValues = _sliderValues.asStateFlow()

    private val _selectedTab = MutableStateFlow<String>(tabs.first())
    val selectedTab = _selectedTab.asStateFlow()

    private val _selectedTabSecond = MutableStateFlow<String>(tabs2.first())
    val selectedTabSecond = _selectedTabSecond.asStateFlow()

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
            is FilterEvent.NavigateYears -> {
                val shift = if (event.backward) -12 else 12
                if (event.isYearFrom) {
                    val firstYear = _visibleYearsFirst.value.first()
                    _visibleYearsFirst.value = generateYears(firstYear + shift, 12)
                } else {
                    val secondYear = _visibleYearsSecond.value.first()
                    _visibleYearsSecond.value = generateYears(secondYear + shift, 12)
                }
            }

            is FilterEvent.OnSliderValueChange -> {
                _sliderValues.value = event.newSliderValue
                Log.i("slider value", _sliderValues.value.toString())
            }

            is FilterEvent.OnTabsSelected -> {
                if (event.isFirst){
                    _selectedTab.value = event.tab
                }else{
                    _selectedTabSecond.value = event.tab
                }
            }

            FilterEvent.OnFilterUsed -> {

            }
        }
    }
}

private fun generateYears(startYear: Int, count: Int): List<Int> {
    return (startYear until startYear + count).toList()
}