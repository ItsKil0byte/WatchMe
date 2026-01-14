package ru.ae.watchme.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.ae.watchme.data.local.converter.GenresConverter
import ru.ae.watchme.domain.model.Movie

@Entity(tableName = "movies")
@TypeConverters(GenresConverter::class)
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String?,
    val description: String?,
    val shortDescription: String?,
    val year: Int?,
    val genres: List<String?>?,
    val posterUrl: String?,
    val previewUrl: String?,
    val rating: Double?,
    val ageRating: Int?
) {
    fun toMovie(): Movie {
        return Movie(
            id = id,
            _name = name,
            _description = description,
            _shortDescription = shortDescription,
            _year = year,
            _genres = genres,
            _posterUrl = posterUrl,
            _previewUrl = previewUrl,
            _rating = rating,
            _ageRating = ageRating
        )
    }
}


