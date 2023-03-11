package com.haliltprkk.movieapplication.data.remote.api_services

import com.haliltprkk.movieapplication.data.remote.models.MovieDto
import com.haliltprkk.movieapplication.data.remote.models.response.PopularMovieListDto
import com.haliltprkk.movieapplication.data.remote.models.response.SearchMovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): PopularMovieListDto

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Long): MovieDto

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String): SearchMovieResponseDto
}
