package sdu.project.cinemaapp.domain.model

import sdu.project.cinemaapp.data.DTO.MovieApiResponse

interface MovieApiResponse {
    val keyword : String
    val pageCount: Int
    val films : List<MovieApiResponse>
}