package com.haliltprkk.movieapplication.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popularMovies")
data class MovieEntity(
    @PrimaryKey val id: Long,
    val genres: List<GenreEntity>?,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String?,
    val title: String,
    val voteAverage: Double
)
