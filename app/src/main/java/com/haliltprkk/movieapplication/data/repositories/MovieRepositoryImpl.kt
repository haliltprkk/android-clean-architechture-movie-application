package com.haliltprkk.movieapplication.data.repositories

import com.haliltprkk.movieapplication.data.api_services.MovieApiService
import com.haliltprkk.movieapplication.data.remote.MovieDto
import com.haliltprkk.movieapplication.data.remote.PopularMovieListDto
import com.haliltprkk.movieapplication.data.remote.SearchMovieDto
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api: MovieApiService) : MovieRepository {
    override suspend fun getPopularMovies(page: Int): PopularMovieListDto = api.getPopularMovies(page)

    override suspend fun getMovie(id: Long): MovieDto = api.getMovie(id)

    override suspend fun searchMovie(query: String): SearchMovieDto = api.searchMovie(query)
}
