package com.haliltprkk.movieapplication.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): PopularMovieListDto

}