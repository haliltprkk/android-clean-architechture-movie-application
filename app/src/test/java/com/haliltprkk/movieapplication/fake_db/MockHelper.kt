package com.haliltprkk.movieapplication.fake_db

import com.haliltprkk.movieapplication.data.models.SearchMovieDto
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

class MockHelper {
    companion object {
        private const val errorJson = "{\"error\":\"\"}"
        val searchMovieDt = SearchMovieDto(0, listOf(), 0, 0)
        val httpException = HttpException(
            Response.error<ResponseBody>(500, errorJson.toResponseBody("plain/text".toMediaTypeOrNull()))
        )
        val ioException = IOException()
    }
}
