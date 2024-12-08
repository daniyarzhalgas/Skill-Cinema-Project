package sdu.project.cinemaapp.domain.model

enum class FilterFilmType(val displayName: String) {
    ALL("Все"),
    FILM("Фильмы"),
    TV_SERIES("Сериалы");

    companion object {
        fun fromDisplayName(name: String): FilterFilmType? {
            return entries.find { it.displayName == name }
        }
    }
}
