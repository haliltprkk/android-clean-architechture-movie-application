package com.haliltprkk.movieapplication.domain.repositories

import com.haliltprkk.movieapplication.data.remote.MovieDto
import com.haliltprkk.movieapplication.data.remote.PopularMovieListDto
import com.haliltprkk.movieapplication.data.remote.SearchMovieDto

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): PopularMovieListDto

    suspend fun getMovie(id: Long): MovieDto

    suspend fun searchMovie(query: String): SearchMovieDto
}
