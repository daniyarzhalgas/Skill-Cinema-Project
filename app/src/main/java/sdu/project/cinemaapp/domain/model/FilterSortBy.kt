package sdu.project.cinemaapp.domain.model

enum class FilterSortBy(val displayName: String) {
    RATING("Рейтинг"),
    NUM_VOTE("Популярность"),
    YEAR("Дата");

    companion object {
        fun fromDisplayName(name: String): FilterSortBy? {
            return FilterSortBy.entries.find { it.displayName == name }
        }
    }
}