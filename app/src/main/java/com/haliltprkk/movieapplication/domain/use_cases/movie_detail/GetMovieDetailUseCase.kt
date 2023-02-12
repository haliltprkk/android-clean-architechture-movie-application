package com.haliltprkk.movieapplication.domain.use_cases.movie_detail

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

class GetMovieDetailUseCase @Inject constructor(private val repository: MovieRepository) {

    operator fun invoke(id: Long): Flow<Resource<Movie>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getMovie(id)
            emit(Resource.Success(data = response.toMovie()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }
}
