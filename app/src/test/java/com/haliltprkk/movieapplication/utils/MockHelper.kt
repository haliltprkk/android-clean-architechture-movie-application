package com.haliltprkk.movieapplication.utils

import com.haliltprkk.movieapplication.data.remote.models.MovieDto
import com.haliltprkk.movieapplication.data.remote.models.response.PopularMovieListDto
import com.haliltprkk.movieapplication.data.remote.models.response.SearchMovieResponseDto
import com.haliltprkk.movieapplication.domain.mappers.MovieMapper
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

class MockHelper {
    companion object {
        private const val errorJson = "{\"error\":\"\"}"
        val searchMovieDt = SearchMovieResponseDto(0, listOf(), 0, 0)
        val ioException = IOException()
        val movieDto = MovieDto(
            adult = false,
            backdropPath = "",
            budget = 0,
            genres = listOf(),
            homepage = "",
            id = 0,
            imdbId = "",
            originalLanguage = "",
            originalTitle = "",
            overview = "",
            popularity = 0.0,
            posterPath = "",
            releaseDate = "",
            revenue = 0,
            runtime = 0,
            status = "",
            tagline = "",
            title = "",
            video = false,
            voteAverage = 0.0
        )
        val movie = MovieMapper().fromDtoToDomain(movieDto)
        val movieList = arrayListOf(movie)
        val popularMovieListDto = PopularMovieListDto(
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
