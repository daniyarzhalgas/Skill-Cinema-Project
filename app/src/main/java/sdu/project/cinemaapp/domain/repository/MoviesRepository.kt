package sdu.project.cinemaapp.domain.repository

import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.domain.model.FilmStaff
import sdu.project.cinemaapp.domain.model.Image
import sdu.project.cinemaapp.domain.model.ImageList
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.SimilarMovie
import sdu.project.cinemaapp.domain.model.SimilarMovies

interface MoviesRepository {
    suspend fun getPremieres(month: String, year: String, page:Int?): List<Movie>
    suspend fun getPopular(type: String, page:Int?): List<Movie>
    suspend fun getMovieById(id: Int): Movie
    suspend fun getActors(id: Int): List<FilmStaff>
    suspend fun getStaff(id: Int): List<FilmStaff>
    suspend fun getActor(id: Int): Actor
    suspend fun getImages(id: Int): List<Image>
    suspend fun getSimilarMovies(id: Int): List<SimilarMovie>
}