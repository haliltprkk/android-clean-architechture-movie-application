package com.haliltprkk.movieapplication.domain.use_cases.search

import com.haliltprkk.movieapplication.common.utils.performRequest
import com.haliltprkk.movieapplication.data.models.toMovie
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import javax.inject.Inject

class SearchMovieUseCaseImpl @Inject constructor(private val repository: MovieRepository) : SearchMovieUseCase {
    override suspend fun searchMovie(query: String) =
        performRequest(
            mapper = { response -> ArrayList(response.results.map { it.toMovie() }) },
            networkCall = { repository.searchMovie(query) },
        )
}
