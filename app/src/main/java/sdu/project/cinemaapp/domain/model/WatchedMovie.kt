package sdu.project.cinemaapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WatchedMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movieId: Int
)
