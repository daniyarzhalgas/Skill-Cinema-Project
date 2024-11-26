package sdu.project.cinemaapp.domain.repository

import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.domain.model.Collection
import sdu.project.cinemaapp.domain.model.FilmStaff
import sdu.project.cinemaapp.domain.model.MovieImage
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.SimilarMovie
import sdu.project.cinemaapp.domain.model.WatchedMovie

interface MoviesRepository {
    suspend fun getPremieres(month: String, year: String, page:Int?): List<Movie>
    suspend fun getPopular(type: String, page:Int?): List<Movie>
    suspend fun getMovieById(id: Int): Movie
    suspend fun getActors(id: Int): List<FilmStaff>
    suspend fun getStaff(id: Int): List<FilmStaff>
    suspend fun getActor(id: Int): Actor
    suspend fun getImages(id: Int): List<MovieImage>
    suspend fun getSimilarMovies(id: Int): List<SimilarMovie>


    suspend fun getMoviesFromCollection(type: String): List<Collection>


    suspend fun setMovie(movie: Movie)
    suspend fun getMovies(movieIds: List<Int>): List<Movie>
    suspend fun setWatchedMovie(movie: WatchedMovie)

}