package com.haliltprkk.movieapplication.data.remote.models

import com.google.gson.annotations.SerializedName

data class MovieDto(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val budget: Int,
    val genres: List<GenreDto>?,
    val homepage: String,
    val id: Long,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double
)
