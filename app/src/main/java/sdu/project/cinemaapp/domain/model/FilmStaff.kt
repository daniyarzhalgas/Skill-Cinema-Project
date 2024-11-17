package sdu.project.cinemaapp.domain.model

data class FilmStaff (
    val staffId: Int,
    val description: String,
    val nameEn: String,
    val nameRu: String,
    val posterUrl: String,
    val professionKey: String,
    val professionText: String
)