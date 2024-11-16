package sdu.project.cinemaapp.domain.model

data class Actor (
    val personId: Int,
    val webUrl: String,
    val nameRu: String,
    val nameEn: String,
    val sex: String,
    val posterUrl: String,
    val growth: Int,
    val birthday: String,
    val death: String,
    val age: Int,
    val birthplace: String,
    val deathplace: String,
    val hasAwards: Int,
    val profession: List<String>,
    val facts: List<Fact>,
    val spouses: List<Spouse>,
    val films: List<Movie>,
)