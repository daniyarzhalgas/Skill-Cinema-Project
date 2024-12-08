package sdu.project.cinemaapp.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import sdu.project.cinemaapp.data.DTO.mapApiResponseToMovie
import sdu.project.cinemaapp.data.local.AppDatabase

import sdu.project.cinemaapp.data.remote.MoviesApi
import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.domain.model.MovieCollection
import sdu.project.cinemaapp.domain.model.Country
import sdu.project.cinemaapp.domain.model.FilmStaff
import sdu.project.cinemaapp.domain.model.Genre
import sdu.project.cinemaapp.domain.model.MovieImage
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.SimilarMovie
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val api: MoviesApi
) : MoviesRepository {

    override suspend fun getPremieres(
        month: String,
        year: String,
        page: Int?
    ): List<Movie> {
        return api.getPremieres(month, year, page).items
    }

    override suspend fun getPopular(type: String, page: Int?): List<Movie> {
        return api.getCollections(type, page).items
    }

    override suspend fun getMovieById(id: Int): Movie {
        val movie = db.movieDao().getMovie(id)

        return movie ?: api.getMovieById(id)
    }

    override suspend fun getActors(id: Int): List<FilmStaff> {
        val allStaff = api.getStaff(id).toList()

        if (allStaff.isEmpty()) {
            Log.e("Actors", "No actors found")
            return emptyList()
        }

        return allStaff.filter { it.professionKey == "ACTOR" }
    }

    override suspend fun getStaff(id: Int): List<FilmStaff> {
        val allStaff = api.getStaff(id).toList()

        if (allStaff.isEmpty()) {
            Log.e("Staff", "No staff found")
            return emptyList()
        }

        return allStaff.filter { it.professionKey != "ACTOR" }
    }

    override suspend fun getActor(id: Int): Actor {
        return api.getActor(id)
    }

    override suspend fun getImages(id: Int): List<MovieImage> {
        return api.getImages(id, null).items
    }

    override suspend fun getSimilarMovies(id: Int): List<SimilarMovie> {
        return api.getSimilarMovies(id).items
    }


    override suspend fun setMovie(movie: Movie) {
        db.movieDao().setMovie(movie)
    }

    override suspend fun getMovies(movieIds: List<Int>): List<Movie> {
        return db.movieDao().getMovies(movieIds)
    }

    override suspend fun getWatchedMovies(): List<Movie> {
        return db.movieDao().getMoviesByWatched()
    }

    override suspend fun deleteAllWatchedMovies() {
        db.movieDao().deleteAllWatchedMovies()
    }

    override suspend fun deleteWatched(id: Int) {
        db.movieDao().deleteFromWatched(id)
    }

    override suspend fun getMoviesByCollection(collection: String): List<Movie> {
        return db.movieDao().getMoviesByCollection(collection)
    }

    override fun getCollectionCount(collection: String): Flow<Int> {
        return db.movieDao().getCollectionCount(collection)
    }

    override suspend fun getFilterFilms(
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
    ): List<Movie> {
        return api.getFilterFilms(
            countries,
            genres,
            order,
            type,
            ratingFrom,
            ratingTo,
            yearFrom,
            yearTo,
            keyword,
            page
        ).items
    }

    override suspend fun searchByKeyword(key: String): List<Movie> {
        val movieApiResponse = api.searchByKeyword(key)

        val movies = movieApiResponse.films.map { mapApiResponseToMovie(it) }
        return movies
    }

    override suspend fun getCountries(): List<Country> {
        val data = api.getFilters()
        val countries = data.countries.map { country -> country }
        return countries
    }

    override suspend fun getGenres(): List<Genre> {
        val data = api.getFilters()
        val genres = data.genres.map { genre -> genre }
        return genres
    }

    override suspend fun upsertCollection(movieCollection: MovieCollection) {
        db.collectionDao().upsert(movieCollection)
    }

    override suspend fun getCollection(collection: String): MovieCollection? {
        return  db.collectionDao().getCollection(collection)
    }

    override fun getCollections(): Flow<List<MovieCollection>> {
        return db.collectionDao().getCollections()
    }

    override suspend fun deleteMovieCollection(collectionName: String) {
        db.movieDao().deleteCollectionAndUpdateMovies(collectionName)
        db.collectionDao().deleteCollection(collectionName)
    }

}