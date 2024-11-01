package sdu.project.cinemaapp.data.DTO

import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.MovieList

class MovieListDto (
    override val items: List<Movie>,
    override val total: Int
): MovieList