package com.haliltprkk.movieapplication.domain.use_cases.home

import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.extension.handleError
import com.haliltprkk.movieapplication.common.utils.Resource
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.data.local.entities.MovieEntity
import com.haliltprkk.movieapplication.data.services.localStorage.LocalStorageService
import com.haliltprkk.movieapplication.domain.mappers.MovieMapper
import com.haliltprkk.movieapplication.domain.models.Movie
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetPopularMoviesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository,
    private val localStorageService: LocalStorageService,
    private val mapper: MovieMapper
) :
    GetPopularMoviesUseCase {
    override suspend fun getPopularMovies(page: Int): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val currentTime = System.currentTimeMillis()
            val lastUpdateTime = localStorageService.getLastUpdateTime() ?: 0
            if (currentTime - lastUpdateTime < CACHE_EXPIRATION_TIME) {
                val cachedMovies: List<MovieEntity> = repository.getCachedPopularMovies(page)
                if (cachedMovies.isNotEmpty()) {
                    emit(Resource.Success(data = cachedMovies.map(mapper::fromEntityToDomain)))
                    return@flow
                }
            }
            val response = repository.getPopularMovies(page = page).results
            repository.deletePopularMoviesFromDatabase()
            repository.savePopularListToCache(response)
            localStorageService.setLastUpdateTime(System.currentTimeMillis())
            emit(Resource.Success(data = response.map(mapper::fromDtoToDomain)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }

    companion object {
        val CACHE_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(5)
    }
}
