package com.haliltprkk.movieapplication.domain.use_cases.home

import com.haliltprkk.movieapplication.common.utils.Resource
import com.haliltprkk.movieapplication.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface GetPopularMoviesUseCase {
    suspend fun getPopularMovies(page: Int): Flow<Resource<ArrayList<Movie>>>
}
