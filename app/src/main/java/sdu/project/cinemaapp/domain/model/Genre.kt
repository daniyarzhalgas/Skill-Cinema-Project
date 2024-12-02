package sdu.project.cinemaapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val genre: String
)
