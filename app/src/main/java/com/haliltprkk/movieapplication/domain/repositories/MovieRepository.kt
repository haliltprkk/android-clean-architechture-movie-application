package com.haliltprkk.movieapplication.domain.repositories

import com.haliltprkk.movieapplication.data.local.entities.MovieEntity
import com.haliltprkk.movieapplication.data.remote.models.MovieDto
import com.haliltprkk.movieapplication.data.remote.models.response.PopularMovieListResponseDto
import com.haliltprkk.movieapplication.data.remote.models.response.SearchMovieResponseDto

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): PopularMovieListResponseDto
    suspend fun getCachedPopularMovies(page: Int): List<MovieEntity>
    suspend fun getMovie(id: Long): MovieDto
    suspend fun searchMovie(query: String): SearchMovieResponseDto
    suspend fun getPopularMoviesFromDatabase(page: Int): List<MovieEntity>
    suspend fun savePopularListToCache(response: List<MovieDto>)
    suspend fun deletePopularMoviesFromDatabase()
}
