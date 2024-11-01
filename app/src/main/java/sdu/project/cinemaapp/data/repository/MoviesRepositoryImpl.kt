package sdu.project.cinemaapp.data.repository

import sdu.project.cinemaapp.data.remote.MoviesApi
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val api: MoviesApi) : MoviesRepository {

    override suspend fun getPremieres(month: String, year: String, page: Int?): List<Movie> {
        return api.getPremieres(month, year, page).items
    }

    override suspend fun getPopular(type: String, page: Int?): List<Movie> {
        return api.getCollections(type, page).items
    }

    override suspend fun getMovieById(id: Int): Movie {
        return api.getMovieById(id)
    }
}