package ru.ae.watchme.data.remote.dto

data class GenreDto(
    val name: String?
)

fun GenreDto.genreToString(): String?{
    return name
}