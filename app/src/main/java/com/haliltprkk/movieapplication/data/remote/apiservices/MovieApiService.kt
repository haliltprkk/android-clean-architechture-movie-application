package com.haliltprkk.movieapplication.data.remote.apiservices

import com.haliltprkk.movieapplication.data.remote.models.MovieDto
import com.haliltprkk.movieapplication.data.remote.models.response.MovieCreditsResponseDto
import com.haliltprkk.movieapplication.data.remote.models.response.PopularMovieListResponseDto
import com.haliltprkk.movieapplication.data.remote.models.response.SearchMovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): PopularMovieListResponseDto

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Long): MovieDto

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String): SearchMovieResponseDto

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(@Path("id") id: Long): MovieCreditsResponseDto
}
