package sdu.project.cinemaapp.domain.repository

import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.domain.model.FilmStaff
import sdu.project.cinemaapp.domain.model.MovieImage
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.SimilarMovie

interface MoviesRepository {
    suspend fun getPremieres(month: String, year: String, page:Int?): List<Movie>
    suspend fun getPopular(type: String, page:Int?): List<Movie>
    suspend fun getMovieById(id: Int): Movie
    suspend fun getActors(id: Int): List<FilmStaff>
    suspend fun getStaff(id: Int): List<FilmStaff>
    suspend fun getActor(id: Int): Actor
    suspend fun getImages(id: Int): List<MovieImage>
    suspend fun getSimilarMovies(id: Int): List<SimilarMovie>
}