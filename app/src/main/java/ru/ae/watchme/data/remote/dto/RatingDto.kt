package ru.ae.watchme.data.remote.dto

data class RatingDto(
    val kp: Double,
    val imdb: Double,
    val filmCritics: Double,
    val russianFilmCritics: Double,
    val await: Double,
)