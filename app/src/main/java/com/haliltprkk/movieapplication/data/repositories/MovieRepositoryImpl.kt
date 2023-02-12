package com.haliltprkk.movieapplication.data.repositories

import com.haliltprkk.movieapplication.data.api_services.MovieApiService
import com.haliltprkk.movieapplication.data.models.MovieDto
import com.haliltprkk.movieapplication.data.models.PopularMovieListDto
import com.haliltprkk.movieapplication.data.models.SearchMovieDto
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MovieApiService) : MovieRepository {
    override suspend fun getPopularMovies(page: Int): PopularMovieListDto {
        return api.getPopularMovies(page)
    }

    override suspend fun getMovie(id: Long): MovieDto {
        return api.getMovie(id)
    }

    override suspend fun searchMovie(query: String): SearchMovieDto {
        return api.searchMovie(query)
    }
}
