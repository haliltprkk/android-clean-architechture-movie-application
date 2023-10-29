package com.haliltprkk.movieapplication.domain.usecases.moviedetail

import com.haliltprkk.movieapplication.common.utils.performRequest
import com.haliltprkk.movieapplication.domain.mappers.MovieCreditsMapper
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import javax.inject.Inject

class GetMovieCastsUseCaseImpl @Inject constructor(
    private val repository: MovieRepository,
    private val mapper: MovieCreditsMapper
) : GetMovieCastsUseCase {
    override suspend fun getCasts(id: Long) =
        performRequest(
            mapper = mapper::fromDtoToDomain,
            networkCall = { repository.getMovieCredits(id) }
        )
}
