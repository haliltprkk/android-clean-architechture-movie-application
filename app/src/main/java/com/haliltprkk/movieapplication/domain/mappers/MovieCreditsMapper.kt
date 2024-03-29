package com.haliltprkk.movieapplication.domain.mappers

import com.haliltprkk.movieapplication.common.extension.EMPTY_STRING
import com.haliltprkk.movieapplication.data.remote.models.response.MovieCreditsResponseDto
import com.haliltprkk.movieapplication.domain.models.Cast

class MovieCreditsMapper {
    fun fromDtoToDomain(creditsResponseDto: MovieCreditsResponseDto): List<Cast> {
        return creditsResponseDto.cast.map {
            Cast(
                character = it.character,
                originalName = it.originalName,
                profilePath = it.profilePath ?: EMPTY_STRING
            )
        }
    }
}
