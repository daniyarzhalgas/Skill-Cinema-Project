package sdu.project.cinemaapp.domain.repository

import kotlinx.coroutines.flow.Flow
import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.domain.model.MovieCollection
import sdu.project.cinemaapp.domain.model.Country
import sdu.project.cinemaapp.domain.model.FilmStaff
import sdu.project.cinemaapp.domain.model.Genre
import sdu.project.cinemaapp.domain.model.MovieImage
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.SimilarMovie

interface MoviesRepository {
    suspend fun getPremieres(month: String, year: String, page: Int?): List<Movie>
    suspend fun getPopular(type: String, page: Int?): List<Movie>
    suspend fun getMovieById(id: Int): Movie
    suspend fun getActors(id: Int): List<FilmStaff>
    suspend fun getStaff(id: Int): List<FilmStaff>
    suspend fun getActor(id: Int): Actor
    suspend fun getImages(id: Int): List<MovieImage>
    suspend fun getSimilarMovies(id: Int): List<SimilarMovie>


    suspend fun setMovie(movie: Movie)
    suspend fun getMovies(movieIds: List<Int>): List<Movie>


    suspend fun getWatchedMovies(): List<Movie>
    suspend fun deleteAllWatchedMovies()
    suspend fun deleteWatched(id: Int)

    suspend fun getMoviesByCollection(collection: String): List<Movie>
    fun getCollectionCount(collection: String): Flow<Int>

    suspend fun getFilterFilms(
        countries: List<Int>,
        genres: List<Int>,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        keyword: String,
        page: Int
    ): List<Movie>

    suspend fun searchByKeyword(key: String): List<Movie>

    suspend fun getCountries(): List<Country>
    suspend fun getGenres(): List<Genre>


    suspend fun upsertCollection(movieCollection: MovieCollection)
    suspend fun getCollection(collection: String): MovieCollection?
    fun getCollections(): Flow<List<MovieCollection>>
    suspend fun deleteMovieCollection(collectionName: String)
}
