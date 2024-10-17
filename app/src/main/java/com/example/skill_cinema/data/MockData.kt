package com.example.skill_cinema.data

object MockData {
    val premier = listOf(
        CinemaItem(1, "Близкие", "драма", 7.8F, "intersleller.jpg"),
        CinemaItem(2, "Близкие", "драма", 7.8F, "intersleller.jpg"),
        CinemaItem(3, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(4, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(5, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(6, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(7, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(8, "Близкие", "драма", 7.8F, "premiere_image_3.jpg")
    )


    val popular = listOf(
        CinemaItem(1, "Близкие", "драма", 7.8F, "intersleller.jpg"),
        CinemaItem(2, "Близкие", "драма", 7.8F, "intersleller.jpg"),
        CinemaItem(3, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(4, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(5, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(6, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(7, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(8, "Близкие", "драма", 7.8F, "premiere_image_3.jpg")
    )


    val actionMovie = listOf(
        CinemaItem(1, "Близкие", "драма", 7.8F, "intersleller.jpg"),
        CinemaItem(2, "Близкие", "драма", 7.8F, "intersleller.jpg"),
        CinemaItem(3, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(4, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(5, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(6, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(7, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(8, "Близкие", "драма", 7.8F, "premiere_image_3.jpg")
    )

    val top250 = listOf(
        CinemaItem(1, "Близкие", "драма", 7.8F, "intersleller.jpg"),
        CinemaItem(2, "Близкие", "драма", 7.8F, "intersleller.jpg"),
        CinemaItem(3, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(4, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(5, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(6, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(7, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(8, "Близкие", "драма", 7.8F, "premiere_image_3.jpg")
    )
    val dramaFrance = listOf(
        CinemaItem(1, "Близкие", "драма", 7.8F, "intersleller.jpg"),
        CinemaItem(2, "Близкие", "драма", 7.8F, "intersleller.jpg"),
        CinemaItem(3, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(4, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(5, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(6, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(7, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(8, "Близкие", "драма", 7.8F, "premiere_image_3.jpg")
    )

    val serial = listOf(
        CinemaItem(1, "Близкие", "драма", 7.8F, "intersleller.jpg"),
        CinemaItem(2, "Близкие", "драма", 7.8F, "intersleller.jpg"),
        CinemaItem(3, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(4, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(5, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(6, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(7, "Близкие", "драма", 7.8F, "premiere_image_3.jpg"),
        CinemaItem(8, "Близкие", "драма", 7.8F, "premiere_image_3.jpg")
    )

    private val allItems = premier + popular + actionMovie + top250 + dramaFrance + serial

    fun findItemById(id: Int): CinemaItem? {
        return allItems.find { it.id == id }
    }
}
