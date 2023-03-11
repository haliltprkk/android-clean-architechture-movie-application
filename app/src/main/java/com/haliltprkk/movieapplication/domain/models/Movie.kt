package com.haliltprkk.movieapplication.domain.models

data class Movie(
    val id: Long,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val title: String,
    val overview: String,
    val genre: String,
    val runtime: Int
)
