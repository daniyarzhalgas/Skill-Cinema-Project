package sdu.project.cinemaapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Spouse (
    @PrimaryKey
    val personId: Int,
    val name: String,
    val divorced: Boolean,
    val divorcedReason: String,
    val sex: String,
    val children: Int,
    val webUrl: String,
    val relation: String
)