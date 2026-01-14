package ru.ae.watchme.domain.repository

import ru.ae.watchme.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(pageNum: Int): List<Movie>
    suspend fun getRandomMovie(): Movie
    suspend fun searchMovie(pageNum: Int, query: String): List<Movie>
    suspend fun getMovieDetails(movieId: Int): Movie
    suspend fun saveMovie(movie: Movie)
    suspend fun getAllMoviesFromDb(): List<Movie>
    suspend fun getMovieByIdFromDb(id: Int): Movie?
}