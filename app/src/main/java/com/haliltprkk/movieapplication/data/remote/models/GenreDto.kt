package com.haliltprkk.movieapplication.data.remote.models

import com.haliltprkk.movieapplication.data.local.entities.GenreEntity

data class GenreDto(
    val id: Int,
    val name: String
)

fun GenreDto.toGenreEntity(): GenreEntity = GenreEntity(
    id = id,
    name = name
)
