package com.haliltprkk.movieapplication.utils

import com.haliltprkk.movieapplication.common.extension.EMPTY_STRING
import com.haliltprkk.movieapplication.data.remote.models.MovieDto
import com.haliltprkk.movieapplication.data.remote.models.response.PopularMovieListResponseDto
import com.haliltprkk.movieapplication.data.remote.models.response.SearchMovieResponseDto
import com.haliltprkk.movieapplication.domain.mappers.MovieMapper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MockHelper {
    companion object {
        private const val errorJson = "{\"error\":\"\"}"
        val searchMovieDt = SearchMovieResponseDto(0, listOf(), 0, 0)
        val ioException = IOException()
        val movieDto = MovieDto(
            adult = false,
            backdropPath = EMPTY_STRING,
            budget = 0,
            genres = listOf(),
            homepage = EMPTY_STRING,
            id = 0,
            imdbId = EMPTY_STRING,
            originalLanguage = EMPTY_STRING,
            originalTitle = EMPTY_STRING,
            overview = EMPTY_STRING,
            popularity = 0.0,
            posterPath = EMPTY_STRING,
            releaseDate = EMPTY_STRING,
            revenue = 0,
            runtime = 0,
            status = EMPTY_STRING,
            tagline = EMPTY_STRING,
            title = EMPTY_STRING,
            video = false,
            voteAverage = 0.0
        )
        val movie = MovieMapper().fromDtoToDomain(movieDto)
        val movieList = arrayListOf(movie)
        val popularMovieListDto = PopularMovieListResponseDto(
            page = 0,
            results = listOf(movieDto),
            totalPages = 0,
            totalResults = 0
        )

        fun getHttpException(): HttpException {
            return HttpException(
                Response.error<ResponseBody>(
                    500,
                    errorJson.toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )
        }
    }
}
