package com.haliltprkk.movieapplication.domain.repository

import com.haliltprkk.movieapplication.data.remote.MovieDto
import com.haliltprkk.movieapplication.data.remote.PopularMovieListDto

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): PopularMovieListDto

    suspend fun getMovie(id: Long): MovieDto
}