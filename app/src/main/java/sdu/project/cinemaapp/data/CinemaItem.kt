package sdu.project.cinemaapp.data

data class CinemaItem(
    val id: Int,
    val title: String,
    val genre: String,
    val rating: Float,
    val imageUrl: String
)