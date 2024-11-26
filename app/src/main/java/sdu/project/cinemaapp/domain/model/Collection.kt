package sdu.project.cinemaapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Collection(
    @PrimaryKey(autoGenerate = true)
    val collectionId: Int,
    val collectionName: String,
    val movieId: Int
)