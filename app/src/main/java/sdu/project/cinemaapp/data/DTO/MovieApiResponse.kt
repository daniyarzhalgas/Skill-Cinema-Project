package sdu.project.cinemaapp.data.DTO

import sdu.project.cinemaapp.domain.model.Country
import sdu.project.cinemaapp.domain.model.Genre
import sdu.project.cinemaapp.domain.model.Movie

data class MovieApiResponse(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val year: String?,
    val filmLength: String?,
    val description: String?,
    val countries: List<Country>?,
    val genres: List<Genre>?,
    val type: String?,
    val ratingVoteCount: Int?,
    val rating : String
)

fun mapApiResponseToMovie(apiResponse: MovieApiResponse): Movie {
    return Movie(
        kinopoiskId = apiResponse.filmId,
        kinopoiskHDId = null,
        nameRu = apiResponse.nameRu,
        nameEn = apiResponse.nameEn,
        nameOriginal = null,
        posterUrl = apiResponse.posterUrl,
        posterUrlPreview = apiResponse.posterUrlPreview,
        year = apiResponse.year?.toInt(),
        filmLength = apiResponse.filmLength?.let { parseFilmLength(it) },
        description = apiResponse.description,
        countries = apiResponse.countries,
        genres = apiResponse.genres,
        type = apiResponse.type,
        ratingKinopoisk = apiResponse.rating.toNullIfLiteralNull()?.toDouble(),
        ratingKinopoiskVoteCount = apiResponse.ratingVoteCount,
        reviewsCount = null,
        imdbId = null,
        coverUrl = null,
        logoUrl = null,
        ratingGoodReview = null,
        ratingGoodReviewVoteCount = null,
        ratingImdb = null,
        ratingImdbVoteCount = null,
        ratingFilmCritics = null,
        ratingFilmCriticsVoteCount = null,
        ratingAwait = null,
        ratingAwaitCount = null,
        ratingRfCritics = null,
        ratingRfCriticsVoteCount = null,
        webUrl = null,
        slogan = null,
        shortDescription = null,
        editorAnnotation = null,
        isTicketsAvailable = null,
        productionStatus = null,
        ratingMpaa = null,
        ratingAgeLimits = null,
        hasImax = null,
        has3D = null,
        lastSync = null,
        startYear = null,
        endYear = null,
        serial = null,
        shortFilm = null,
        completed = null,
        professionKey = null,
        isWatched = false,
        collectionName = null
    )
}

fun parseFilmLength(filmLength: String): Int? {
    return try {
        val parts = filmLength.split(":")
        val hours = parts[0].toInt()
        val minutes = parts[1].toInt()
        hours * 60 + minutes
    } catch (e: Exception) {
        null
    }
}

fun String?.toNullIfLiteralNull() : String?{
    return if (this == "null") null else this
}