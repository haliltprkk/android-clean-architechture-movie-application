package com.haliltprkk.movieapplication.data.repository

import com.haliltprkk.movieapplication.data.remote.MovieDto
import com.haliltprkk.movieapplication.data.remote.MovieService
import com.haliltprkk.movieapplication.data.remote.PopularMovieListDto
import com.haliltprkk.movieapplication.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MovieService) : MovieRepository {
    override suspend fun getPopularMovies(page: Int): PopularMovieListDto {
        return api.getPopularMovies(page)
    }

    override suspend fun getMovie(id: Long): MovieDto {
        return api.getMovie(id)
    }
}