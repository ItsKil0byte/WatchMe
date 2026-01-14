package ru.ae.watchme.domain.model

data class Movie(
    val id: Int,
    private val _name: String?,
    private val _description: String?,
    private val _shortDescription: String?,
    private val _year: Int?,
    private val _genres: List<String?>?,
    private val _posterUrl: String?,
    private val _previewUrl: String?,
    private val _rating: Double?,
    private val _ageRating: Int?
) {
    val name: String
        get() = if (!_name.isNullOrBlank()) _name else "Без названия"

    val description: String
        get() = when {
            !_description.isNullOrBlank() -> _description
            !_shortDescription.isNullOrBlank() -> _shortDescription
            else -> "Описание отсутствует"
        }

    val shortDescription: String
        get() = when {
            !_shortDescription.isNullOrBlank() -> _shortDescription
            !_description.isNullOrBlank() -> _description
            else -> "Описание отсутствует"
        }

    val year: String
        get() = if (_year != null && _year != 0) _year.toString() else "Год неизвестен"

    val genres: String
        get() = _genres?.filterNotNull()?.joinToString(", ") ?: "Жанры не указаны"

    val posterUrl: String
        get() = if (!_posterUrl.isNullOrBlank()) _posterUrl else "https://placecats.com/neo_2/300/400"

    val previewUrl: String
        get() = if (!_previewUrl.isNullOrBlank()) _previewUrl else "https://placecats.com/neo_2/150/200"

    val rating: String
        get() = if (_rating != null && _rating != 0.0) _rating.toString() else "-"

    val ageRating: String
        get() = if (_ageRating != null && _ageRating != 0) "$_ageRating+" else ""
}