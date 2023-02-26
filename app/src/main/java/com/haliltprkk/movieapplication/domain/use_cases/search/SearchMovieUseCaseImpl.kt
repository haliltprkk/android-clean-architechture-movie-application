package com.haliltprkk.movieapplication.domain.use_cases.search

import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.Resource
import com.haliltprkk.movieapplication.common.UiText
import com.haliltprkk.movieapplication.common.extension.handleError
import com.haliltprkk.movieapplication.data.models.toMovie
import com.haliltprkk.movieapplication.domain.models.Movie
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class SearchMovieUseCaseImpl @Inject constructor(private val repository: MovieRepository) : SearchMovieUseCase {

    override suspend fun searchMovie(query: String): Flow<Resource<ArrayList<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.searchMovie(query)
            emit(Resource.Success(data = ArrayList(response.results.map { it.toMovie() })))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }
}
