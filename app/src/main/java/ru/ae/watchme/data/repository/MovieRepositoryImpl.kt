package ru.ae.watchme.data.repository

import ru.ae.watchme.data.local.dao.MovieDao
import ru.ae.watchme.data.local.model.toMovie
import ru.ae.watchme.data.remote.dto.toDomain
import ru.ae.watchme.data.remote.service.MovieService
import ru.ae.watchme.domain.model.Movie
import ru.ae.watchme.domain.model.toMovieEntity
import ru.ae.watchme.domain.repository.MovieRepository

class MovieRepositoryImpl (
    private val movieService: MovieService,
    private val movieDao: MovieDao
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

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return try {
            val response = movieService.getMovieDetails(movieId)
            response.toDomain()
        } catch (e: Exception) {
            throw RuntimeException("Чето не так с получением фильма по айди, поплачь хз", e)
        }
    }

    override suspend fun saveMovie(movie: Movie) {
        return movieDao.saveMovie(movie.toMovieEntity())
    }

    override suspend fun getAllMoviesFromDb(): List<Movie> {
        return movieDao.getAllMovies().map { it.toMovie() }
    }

    override suspend fun getMovieByIdFromDb(id: Int): Movie? {
        return movieDao.getMovieById(id)?.toMovie()
    }
}