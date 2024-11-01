package sdu.project.cinemaapp.domain.model

interface MovieList {
    val items: List<Movie>
    val total: Int
}