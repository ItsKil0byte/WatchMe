package ru.ae.watchme.data.repository

import ru.ae.watchme.data.remote.dto.toDomain
import ru.ae.watchme.data.remote.service.MovieService
import ru.ae.watchme.domain.model.Movie
import ru.ae.watchme.domain.repository.MovieRepository

class MovieRepositoryImpl (
    private val movieService: MovieService
) : MovieRepository{
    override suspend fun getMovies(pageNum: Int): List<Movie> {
        return try{
            val movieDtos = movieService.getMovies(pageNum)
            movieDtos.movies.map { it.toDomain() }
        } catch(e: Exception){
            throw RuntimeException("Чето не так с получением киношек", e)
        }
    }

    override suspend fun getRandomMovie(): Movie {
        return try{
            val response = movieService.getRandomMovie()
            response.toDomain()
        } catch (e: Exception){
            throw RuntimeException("Чето не так с получением случайной киношки", e)
        }
    }

    override suspend fun searchMovie(
        pageNum: Int,
        query: String
    ): List<Movie> {
        return try {
            val response = movieService.searchMovie(pageNum = pageNum, query = query)
            response.movies.map { it.toDomain() }
        } catch (e: Exception){
            throw RuntimeException("Чето не так с поиском киношки", e)
        }
    }
}