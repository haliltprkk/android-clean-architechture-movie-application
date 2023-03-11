package com.haliltprkk.movieapplication.data.remote.models.response
import com.google.gson.annotations.SerializedName
import com.haliltprkk.movieapplication.data.remote.models.MovieDto

data class PopularMovieListDto(
    val page: Int,
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
