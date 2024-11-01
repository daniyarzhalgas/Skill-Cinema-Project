package sdu.project.cinemaapp.domain.repository

import sdu.project.cinemaapp.domain.model.Movie

interface MoviesRepository {
    suspend fun getPremieres(month: String, year: String, page:Int?): List<Movie>
    suspend fun getPopular(type: String, page:Int?): List<Movie>
    suspend fun getMovieById(id: Int): Movie
}