package com.haliltprkk.movieapplication.domain.use_case.search

import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.Resource
import com.haliltprkk.movieapplication.common.UiText
import com.haliltprkk.movieapplication.common.extension.handleError
import com.haliltprkk.movieapplication.data.remote.toMovie
import com.haliltprkk.movieapplication.domain.model.Movie
import com.haliltprkk.movieapplication.domain.repository.MovieRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class SearchMovieUseCase @Inject constructor(private val repository: MovieRepository) {

    operator fun invoke(query: String): Flow<Resource<ArrayList<Movie>>> = flow {
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
