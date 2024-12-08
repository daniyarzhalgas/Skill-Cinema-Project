package sdu.project.cinemaapp.presentation.ui.search.mainPage

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.ignoreIoExceptions
import sdu.project.cinemaapp.domain.model.Country
import sdu.project.cinemaapp.domain.model.FilterFilmType
import sdu.project.cinemaapp.domain.model.FilterSortBy
import sdu.project.cinemaapp.domain.model.Genre
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import sdu.project.cinemaapp.presentation.state.ScreenState
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private var isDataLoaded: Boolean = false

    private fun fetchData() {
        if (isDataLoaded)
            return
        getCountriesGenres()
        isDataLoaded = true
    }


    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>?>(emptyList())
    val movies = _movies.asStateFlow()

    var searchText by mutableStateOf("")
        private set


    val tabs = listOf("Все", "Фильмы", "Сериалы")
    val tabs2 = listOf("Дата", "Популярность", "Рейтинг")

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries = _countries.asStateFlow()

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres = _genres.asStateFlow()


    private val _country = MutableStateFlow("")
    val country = _country.asStateFlow()

    private val _genre = MutableStateFlow("")
    val genre = _genre.asStateFlow()

    private val _yearFrom = MutableStateFlow<Int>(1998)
    val yearFrom = _yearFrom.asStateFlow()

    private val _yearTo = MutableStateFlow<Int>(2009)
    val yearTo = _yearTo.asStateFlow()

    private val _visibleYearsFirst = MutableStateFlow<List<Int>>(generateYears(1998, 12))
    val visibleYearsFirst = _visibleYearsFirst.asStateFlow()

    private val _visibleYearsSecond = MutableStateFlow<List<Int>>(generateYears(1998, 12))
    val visibleYearsSecond = _visibleYearsSecond.asStateFlow()

    private val _sliderValues = MutableStateFlow(1f..10f)
    val sliderValues = _sliderValues.asStateFlow()

    private val _selectedTab = MutableStateFlow<FilterFilmType>(FilterFilmType.ALL)
    val selectedTab = _selectedTab.asStateFlow()

    private val _selectedTabSecond = MutableStateFlow<FilterSortBy>(FilterSortBy.YEAR)
    val selectedTabSecond = _selectedTabSecond.asStateFlow()


    fun event(navController: NavHostController, event: SearchEvent) {
        when (event) {
            is SearchEvent.OnFilterClick -> {
                navController.navigate("filter")
            }

            is SearchEvent.OnBackClicked -> navController.popBackStack()

            is SearchEvent.OnSearchTextChange -> {
                searchText = event.text
            }

            is SearchEvent.OnMovieClick -> {
                navController.navigate("details/${event.movieId}")
            }

            is SearchEvent.OnCountrySelected -> {
                _country.value = event.title
            }

            is SearchEvent.OnGenreSelected -> {
                _genre.value = event.genre
            }

            is SearchEvent.OnGenreClicked -> {
                navController.navigate("filter_genre")
            }

            is SearchEvent.OnYearClicked -> navController.navigate("filter_period")
            is SearchEvent.OnYearSelected -> {
                if (event.isYearFrom) _yearFrom.value = event.year else _yearTo.value = event.year
            }

            is SearchEvent.NavigateYears -> {
                val shift = if (event.backward) -12 else 12
                if (event.isYearFrom) {
                    val firstYear = _visibleYearsFirst.value.first()
                    _visibleYearsFirst.value = generateYears(firstYear + shift, 12)
                } else {
                    val secondYear = _visibleYearsSecond.value.first()
                    _visibleYearsSecond.value = generateYears(secondYear + shift, 12)
                }
            }

            is SearchEvent.OnSliderValueChange -> {
                _sliderValues.value = event.newSliderValue
                Log.i("slider value", _sliderValues.value.toString())
            }

            is SearchEvent.OnTabsSelected -> {
                if (event.isFirst) {
                    _selectedTab.value = FilterFilmType.fromDisplayName(event.tab)!!
                } else {
                    _selectedTabSecond.value = FilterSortBy.fromDisplayName(event.tab)!!
                }
            }

            is SearchEvent.OnCountryClicked ->
                navController.navigate("filter_country")

            SearchEvent.OnFilterUsed -> {
                fetchFilteredMovies()
                navController.popBackStack()
            }
        }
    }

    init {
        observeSearchTextChanges()
        fetchData()
    }

    private fun observeSearchTextChanges() {
        viewModelScope.launch {
            snapshotFlow { searchText }
                .debounce(500)
                .filterNotNull()
                .filter { it.isNotBlank() }
                .collectLatest {
                    fetchSearchingMovies(it)
                }
        }
    }

    private suspend fun fetchSearchingMovies(query: String) {
        try {
            _state.value = ScreenState.Loading
            val moviesResult = moviesRepository.searchByKeyword(query)
            _movies.value = moviesResult
            _state.value = ScreenState.Success
        } catch (e: Exception) {
            Log.e("SearchViewModel", "Error: $e")
            _state.value = ScreenState.Error
        }
    }

    private fun fetchFilteredMovies() {
        try {
            _state.value = ScreenState.Loading
            filterMovies()
            _state.value = ScreenState.Success
        } catch (e: Exception) {
            Log.e("SearchViewModel", "Error: $e")
            _state.value = ScreenState.Error
        }
    }

    private fun generateYears(startYear: Int, count: Int): List<Int> {
        return (startYear until startYear + count).toList()
    }

    private fun getCountriesGenres() {
        viewModelScope.launch {
            try {
                val fetchedCountries = moviesRepository.getCountries()
                _countries.value = fetchedCountries

                val fetchedGenres = moviesRepository.getGenres()
                _genres.value = fetchedGenres

            } catch (e: Exception) {
                Log.e("getCountriesGenres", "Error: ${e.message}")
            }
        }
    }

    private fun filterMovies() {
        viewModelScope.launch {


            val filteredCountryIds = countries.value
                .filter { country ->
                    _country.value.isBlank() || country.country == _country.value
                }
                .map { it.id }

            val filteredGenreIds = genres.value
                .filter { genre ->
                    _genre.value.isBlank() || genre.genre == _genre.value
                }
                .map { it.id }


            try {
                val movies = moviesRepository.getFilterFilms(
                    filteredCountryIds,
                    filteredGenreIds,
                    selectedTabSecond.value.toString(),
                    selectedTab.value.toString(),
                    sliderValues.value.start.toInt(),
                    sliderValues.value.endInclusive.toInt(),
                    yearFrom.value,
                    yearTo.value,
                    searchText,
                    1
                )
                Log.i("filterMovies", movies.toString())
                _movies.value = movies
            } catch (e: Exception) {
                Log.e("filterMovies", "Error: ${e.message}")
            }
        }
    }
}