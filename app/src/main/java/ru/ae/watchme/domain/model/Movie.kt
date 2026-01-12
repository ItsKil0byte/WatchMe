package ru.ae.watchme.domain.model

data class Movie(
    val id: Int,
    val name: String,
    val description: String,
    val shortDescription: String,
    val year: Int,
    val genres: List<String>,
    val posterUrl: String,
    val previewUrl: String,
    val rating: Double,
    val ageRating: Int
)