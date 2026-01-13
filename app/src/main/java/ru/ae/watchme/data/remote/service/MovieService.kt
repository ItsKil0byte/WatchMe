package ru.ae.watchme.data.remote.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ae.watchme.data.remote.constant.Constants
import ru.ae.watchme.data.remote.dto.MovieDto
import ru.ae.watchme.data.remote.dto.MovieResponseDto

interface MovieService {
    @GET("movie")
    suspend fun getMovies(
        @Query("page") pageNum: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("selectFields") fields: List<String> = Constants.selectFields,
        @Query("notNullFields") notNullFields: List<String> = Constants.notNullFields
    ): MovieResponseDto

    @GET("movie/random")
    suspend fun getRandomMovie(
        @Query("notNullFields") notNullFields: List<String> = Constants.notNullFields
    ): MovieDto

    @GET("movie/search")
    suspend fun searchMovie(
        @Query("page") pageNum: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("query") query: String
    ): MovieResponseDto

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int
    ): MovieDto

}