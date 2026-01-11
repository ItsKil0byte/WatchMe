package ru.ae.watchme.data.remote.dto

data class RatingDto(
    val kp: Int,
    val imdb: Int,
    val filmCritics: Int,
    val russianFilmCritics: Int,
    val await: Int,
)