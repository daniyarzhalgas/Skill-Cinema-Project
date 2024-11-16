package sdu.project.cinemaapp.domain.model

interface SimilarMovies{
    val total: Int
    val items: List<SimilarMovie>
}
