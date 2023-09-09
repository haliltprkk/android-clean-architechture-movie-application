package com.haliltprkk.movieapplication.domain.use_cases.search

import com.haliltprkk.movieapplication.common.utils.performRequest
import com.haliltprkk.movieapplication.domain.mappers.MovieMapper
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import javax.inject.Inject

class SearchMovieUseCaseImpl @Inject constructor(
    private val repository: MovieRepository,
    private val mapper: MovieMapper
) : SearchMovieUseCase {
    override suspend fun searchMovie(query: String) =
        performRequest(
            mapper = { response -> response.results.map(mapper::fromDtoToDomain) },
            networkCall = { repository.searchMovie(query) }
        )
}
