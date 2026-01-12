package ru.ae.watchme.data.remote.dto

import ru.ae.watchme.domain.model.Movie

data class MovieDto(
    val id: Int,
    val name: String,
    val description: String,
    val shortDescription: String,
    val year: Int,
    val genres: List<GenreDto>,
    val poster: PosterDto,
    val rating: RatingDto,
    val ageRating: Int
)

fun MovieDto.toDomain(): Movie{
    return Movie(
        id = id,
        name = name,
        description = description,
        shortDescription = shortDescription,
        year = year,
        genres = genres.map { it.genreToString() },
        posterUrl = poster.url,
        previewUrl = poster.previewUrl,
        rating = rating.kp,
        ageRating = ageRating
    )
}