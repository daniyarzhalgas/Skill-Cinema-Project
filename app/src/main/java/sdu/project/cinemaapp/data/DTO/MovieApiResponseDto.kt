package sdu.project.cinemaapp.data.DTO

import sdu.project.cinemaapp.domain.model.MovieApiResponse

class MovieApiResponseDto (
    override val keyword: String,
    override val pageCount: Int,
    override val films: List<sdu.project.cinemaapp.data.DTO.MovieApiResponse>
): MovieApiResponse