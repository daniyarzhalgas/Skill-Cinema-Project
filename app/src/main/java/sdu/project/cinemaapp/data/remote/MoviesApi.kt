package sdu.project.cinemaapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import sdu.project.cinemaapp.data.DTO.CountryGenreDto
import sdu.project.cinemaapp.data.DTO.FilterFilmsDto
import sdu.project.cinemaapp.data.DTO.ImageListDto
import sdu.project.cinemaapp.data.DTO.MovieApiResponse
import sdu.project.cinemaapp.data.DTO.MovieApiResponseDto
import sdu.project.cinemaapp.data.DTO.MovieListDto
import sdu.project.cinemaapp.data.DTO.SimilarMoviesDto
import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.domain.model.FilmStaff
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.utills.constant.Constant.API_KEY

interface MoviesApi {
    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("month") month: String,
        @Query("year") year: String,
        @Query("page") page: Int?
    ): MovieListDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/collections")
    suspend fun getCollections(
        @Query("type") type: String,
        @Query("page") page: Int?
    ): MovieListDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int
    ): Movie

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v1/staff")
    suspend fun getStaff(
        @Query("filmId") filmId: Int
    ): List<FilmStaff>

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v1/staff/{id}")
    suspend fun getActor(
        @Path("id") actorId: Int
    ):Actor

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/{id}/images")
    suspend fun getImages(
        @Path("id") filmId: Int,
        @Query("page") page: Int?
    ):ImageListDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilarMovies(
        @Path("id") filmId: Int,
    ): SimilarMoviesDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun searchByKeyword(
        @Query("keyword") keyword: String,
        @Query("page") page: Int = 1
    ): MovieApiResponseDto

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/filters")
    suspend fun getFilters(): CountryGenreDto


    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films")
    suspend fun getFilterFilms(
        @Query("countries") countries: List<Int>,
        @Query("genres") genres: List<Int>,
        @Query("order") order : String,
        @Query("type") type : String,
        @Query("ratingFrom") ratingFrom : Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("keyword") keyword: String,
        @Query("page") page: Int = 1
    ): FilterFilmsDto
}