package sdu.project.cinemaapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SimilarMovie(
    @PrimaryKey
    val filmId: Int,
    val nameRu: String,
    val nameEn: String,
    val nameOriginal: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val relationType: String
)
