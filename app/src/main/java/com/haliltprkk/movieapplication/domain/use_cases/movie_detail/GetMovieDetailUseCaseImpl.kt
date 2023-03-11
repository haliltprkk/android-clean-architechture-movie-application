package com.haliltprkk.movieapplication.domain.use_cases.movie_detail

import com.haliltprkk.movieapplication.common.utils.performRequest
import com.haliltprkk.movieapplication.domain.mappers.MovieMapper
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCaseImpl @Inject constructor(
    private val repository: MovieRepository,
    private val mapper: MovieMapper
) : GetMovieDetailUseCase {
    override suspend fun getMovieById(id: Long) =
        performRequest(
            mapper = mapper::fromDtoToDomain,
            networkCall = { repository.getMovie(id) },
        )
}
