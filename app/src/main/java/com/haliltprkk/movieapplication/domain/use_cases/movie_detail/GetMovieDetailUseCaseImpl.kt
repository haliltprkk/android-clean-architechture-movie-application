package com.haliltprkk.movieapplication.domain.use_cases.movie_detail

import com.haliltprkk.movieapplication.common.utils.performRequest
import com.haliltprkk.movieapplication.data.models.toMovie
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCaseImpl @Inject constructor(private val repository: MovieRepository) : GetMovieDetailUseCase {
    override suspend fun getMovieById(id: Long) =
        performRequest(
            mapper = { response -> response.toMovie() },
            networkCall = { repository.getMovie(id) },
        )
}
