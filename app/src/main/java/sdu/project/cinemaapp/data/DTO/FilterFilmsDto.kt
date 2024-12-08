package sdu.project.cinemaapp.data.DTO

import sdu.project.cinemaapp.domain.model.Movie

class FilterFilmsDto(
    val total: Int,
    val totalPages: Int,
    val items: List<Movie>
)