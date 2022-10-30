package com.haliltprkk.movieapplication.domain.repository

import com.haliltprkk.movieapplication.data.remote.PopularMovieListDto

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): PopularMovieListDto
}