package com.haliltprkk.movieapplication.domain.use_cases.home

import com.haliltprkk.movieapplication.common.utils.performRequest
import com.haliltprkk.movieapplication.data.remote.toMovie
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCaseImpl @Inject constructor(private val repository: MovieRepository) :
    GetPopularMoviesUseCase {
    override suspend fun getPopularMovies(page: Int) =
        performRequest(
            mapper = { response -> ArrayList(response.results.map { it.toMovie() }) },
            networkCall = { repository.getPopularMovies(page = page) },
        )
}
