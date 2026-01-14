package ru.ae.watchme.data.remote.dto

import ru.ae.watchme.domain.model.Movie

data class MovieDto(
    val id: Int,
    val name: String?,
    val description: String?,
    val shortDescription: String?,
    val year: Int?,
    val genres: List<GenreDto>?,
    val poster: PosterDto?,
    val rating: RatingDto?,
    val ageRating: Int?
)

fun MovieDto.toDomain(): Movie{
    return Movie(
        id = id,
        _name = name,
        _description = description,
        _shortDescription = shortDescription,
        _year = year,
        _genres = genres?.map { it.genreToString() },
        _posterUrl = poster?.url,
        _previewUrl = poster?.previewUrl,
        _rating = rating?.kp,
        _ageRating = ageRating
    )
}