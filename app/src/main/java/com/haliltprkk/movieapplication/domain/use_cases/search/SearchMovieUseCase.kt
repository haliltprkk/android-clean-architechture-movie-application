package com.haliltprkk.movieapplication.domain.use_cases.search

import com.haliltprkk.movieapplication.common.utils.Resource
import com.haliltprkk.movieapplication.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface SearchMovieUseCase {
    suspend fun searchMovie(query: String): Flow<Resource<ArrayList<Movie>>>
}
