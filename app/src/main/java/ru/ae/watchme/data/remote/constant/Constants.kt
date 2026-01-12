package ru.ae.watchme.data.remote.constant

object Constants {
    val selectFields = listOf(
        "id",
        "name",
        "description",
        "shortDescription",
        "year",
        "genres",
        "poster",
        "rating",
        "ageRating"
    )

    val notNullFields = listOf(
        "id",
        "name",
        "description",
        "shortDescription",
        "year",
        "genres.name",
        "poster.url",
        "rating.kp",
        "ageRating"
    )
}