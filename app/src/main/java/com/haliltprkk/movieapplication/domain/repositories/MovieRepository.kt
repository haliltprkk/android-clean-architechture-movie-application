package com.haliltprkk.movieapplication.domain.repositories

import com.haliltprkk.movieapplication.data.models.MovieDto
import com.haliltprkk.movieapplication.data.models.PopularMovieListDto
import com.haliltprkk.movieapplication.data.models.SearchMovieDto

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): PopularMovieListDto

    suspend fun getMovie(id: Long): MovieDto

    suspend fun searchMovie(query: String): SearchMovieDto
}
