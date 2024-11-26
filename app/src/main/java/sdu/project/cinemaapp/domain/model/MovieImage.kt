package sdu.project.cinemaapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieImage(
    @PrimaryKey
    val id: Int,
    val imageUrl: String,
    val previewUrl: String
)