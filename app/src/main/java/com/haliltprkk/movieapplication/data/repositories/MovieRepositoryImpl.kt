package com.haliltprkk.movieapplication.data.repositories

import com.haliltprkk.movieapplication.data.local.MovieDao
import com.haliltprkk.movieapplication.data.local.entities.MovieEntity
import com.haliltprkk.movieapplication.data.remote.api_services.MovieApiService
import com.haliltprkk.movieapplication.data.remote.models.MovieDto
import com.haliltprkk.movieapplication.data.remote.models.response.PopularMovieListDto
import com.haliltprkk.movieapplication.data.remote.models.response.SearchMovieResponseDto
import com.haliltprkk.movieapplication.domain.mappers.MovieMapper
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApiService,
    private val dao: MovieDao,
    private val mapper: MovieMapper
) :
    MovieRepository {
    override suspend fun getPopularMovies(page: Int): PopularMovieListDto = api.getPopularMovies(page)
    override suspend fun getCachedPopularMovies(page: Int): List<MovieEntity> = dao.getPopularMovies()
    override suspend fun getPopularMoviesFromDatabase(page: Int): List<MovieEntity> = dao.getPopularMovies()
    override suspend fun savePopularListToCache(response: List<MovieDto>) =
        dao.insertMovies(response.map { mapper.fromDtoToEntity(it) })

    override suspend fun deletePopularMoviesFromDatabase() = dao.deleteAllMovies()
    override suspend fun getMovie(id: Long): MovieDto = api.getMovie(id)
    override suspend fun searchMovie(query: String): SearchMovieResponseDto = api.searchMovie(query)
}
