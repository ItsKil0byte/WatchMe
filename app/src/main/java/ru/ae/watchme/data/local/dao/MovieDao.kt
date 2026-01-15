package ru.ae.watchme.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import ru.ae.watchme.data.local.model.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = REPLACE)
    suspend fun saveMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM movies WHERE ID = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteMovieById(id: Int)
}