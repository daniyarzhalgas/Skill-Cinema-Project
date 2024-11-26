package sdu.project.cinemaapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actors")
data class Actor (
    @PrimaryKey
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
    val spouses: List<Spouse>,
    val hasAwards: Int,
    val profession:String,
    val facts: List<String>,
    val films: List<ActorsMovie>,
)