package com.haliltprkk.movieapplication.data.remote
import com.google.gson.annotations.SerializedName

data class PopularMovieListDto(
    val page: Int,
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
