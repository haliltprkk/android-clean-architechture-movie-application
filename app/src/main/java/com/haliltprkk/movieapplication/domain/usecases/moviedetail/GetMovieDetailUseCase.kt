package com.haliltprkk.movieapplication.domain.usecases.moviedetail

import com.haliltprkk.movieapplication.common.utils.Resource
import com.haliltprkk.movieapplication.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailUseCase {
    suspend fun getMovieById(id: Long): Flow<Resource<Movie>>
}
