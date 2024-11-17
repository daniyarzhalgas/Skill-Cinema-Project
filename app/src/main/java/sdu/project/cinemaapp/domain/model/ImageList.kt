package sdu.project.cinemaapp.domain.model

interface ImageList {
    val total: Int
    val totalPages: Int
    val items: List<MovieImage>
}