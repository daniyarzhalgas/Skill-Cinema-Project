package sdu.project.cinemaapp.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = ["collectionName"])
    ],
)
data class MovieCollection(
    @PrimaryKey
    val collectionName: String
)
