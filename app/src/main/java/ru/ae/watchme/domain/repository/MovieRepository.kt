package ru.ae.watchme.domain.repository

import ru.ae.watchme.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(pageNum: Int): List<Movie>
}