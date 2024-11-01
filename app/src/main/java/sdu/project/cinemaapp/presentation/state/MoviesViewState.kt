package sdu.project.cinemaapp.presentation.state

import sdu.project.cinemaapp.domain.model.Movie

data class MoviesViewState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList()
)