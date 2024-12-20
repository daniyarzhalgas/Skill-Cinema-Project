package sdu.project.cinemaapp.domain.model

import androidx.room.Entity

@Entity(tableName = "countries")
data class Country(
    val id: Int,
    val country: String
)
