package sdu.project.cinemaapp.data.DTO

import sdu.project.cinemaapp.domain.model.SimilarMovie
import sdu.project.cinemaapp.domain.model.SimilarMovies

class SimilarMoviesDto(
    override val total: Int,
    override val items: List<SimilarMovie>
) : SimilarMovies