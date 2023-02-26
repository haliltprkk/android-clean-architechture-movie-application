package com.haliltprkk.movieapplication.domain.use_cases.movie_detail

import com.haliltprkk.movieapplication.common.Resource
import com.haliltprkk.movieapplication.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailUseCase {
    suspend fun getMovieById(id: Long): Flow<Resource<Movie>>
}
