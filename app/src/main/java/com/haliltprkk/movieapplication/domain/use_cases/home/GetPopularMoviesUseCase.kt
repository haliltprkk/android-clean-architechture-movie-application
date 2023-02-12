package com.haliltprkk.movieapplication.domain.use_cases.home

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

class GetPopularMoviesUseCase @Inject constructor(private val repository: MovieRepository) {

    operator fun invoke(page: Int): Flow<Resource<ArrayList<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val response = ArrayList(repository.getPopularMovies(page = page).results.map { it.toMovie() })
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }
}
