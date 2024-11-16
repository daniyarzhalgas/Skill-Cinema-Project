package sdu.project.cinemaapp.data.repository

import sdu.project.cinemaapp.data.remote.MoviesApi
import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.domain.model.FilmStaff
import sdu.project.cinemaapp.domain.model.Image
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.SimilarMovie
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

    override suspend fun getActors(id: Int): List<FilmStaff> {
        return api.getStaff(id)
            .filter { it.professionKey == "ACTOR" }
    }

    override suspend fun getStaff(id: Int): List<FilmStaff> {
        return api.getStaff(id)
    }

    override suspend fun getActor(id: Int): Actor {
        return api.getActor(id)
    }

    override suspend fun getImages(id: Int):List<Image>{
        return api.getImages(id, null).items
    }

    override suspend fun getSimilarMovies(id: Int): List<SimilarMovie> {
        return api.getSimilarFilms(id).items
    }
}