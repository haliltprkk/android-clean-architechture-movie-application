package com.haliltprkk.movieapplication.domain.usecases.moviedetail

import com.haliltprkk.movieapplication.common.utils.Resource
import com.haliltprkk.movieapplication.domain.models.Cast
import kotlinx.coroutines.flow.Flow

interface GetMovieCastsUseCase {
    suspend fun getCasts(id: Long): Flow<Resource<List<Cast>>>
}
