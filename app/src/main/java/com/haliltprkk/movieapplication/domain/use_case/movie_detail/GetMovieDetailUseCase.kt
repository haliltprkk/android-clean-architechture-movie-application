package com.haliltprkk.movieapplication.domain.use_case.movie_detail

import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.Resource
import com.haliltprkk.movieapplication.common.UiText
import com.haliltprkk.movieapplication.common.extension.handleError
import com.haliltprkk.movieapplication.data.remote.toMovie
import com.haliltprkk.movieapplication.domain.model.Movie
import com.haliltprkk.movieapplication.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

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
