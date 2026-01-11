package ru.ae.watchme.data.remote.dto

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