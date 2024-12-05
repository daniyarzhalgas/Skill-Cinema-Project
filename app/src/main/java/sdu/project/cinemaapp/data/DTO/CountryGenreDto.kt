package sdu.project.cinemaapp.data.DTO

import sdu.project.cinemaapp.domain.model.Country
import sdu.project.cinemaapp.domain.model.Genre

class CountryGenreDto (
    val countries : List<Country>,
    val genres : List<Genre>
)