package com.haliltprkk.movieapplication.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): PopularMovieListDto

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Long): MovieDto

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String): SearchMovieDto
}
