package com.haliltprkk.movieapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.haliltprkk.movieapplication.data.remote.GenreDto

@Entity
data class MovieEntity(
    @PrimaryKey val id: Long,
    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollection: Any,
    val budget: Int,
    val genres: List<GenreDto>?,
    val homepage: String,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompanyEntity>,
    val productionCountries: List<ProductionCompanyEntity>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguageEntity>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
