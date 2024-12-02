package sdu.project.cinemaapp.data.repository

import android.util.Log
import coil.network.HttpException
import kotlinx.coroutines.flow.Flow
import okio.IOException
import sdu.project.cinemaapp.data.local.AppDatabase

import sdu.project.cinemaapp.data.remote.MoviesApi
import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.domain.model.FilmStaff
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


}