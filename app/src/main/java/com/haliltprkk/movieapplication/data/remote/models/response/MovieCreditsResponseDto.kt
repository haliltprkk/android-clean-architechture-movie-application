package com.haliltprkk.movieapplication.data.remote.models.response

import com.haliltprkk.movieapplication.data.remote.models.CastDto
import com.haliltprkk.movieapplication.data.remote.models.CrewDto

data class MovieCreditsResponseDto(
    val cast: List<CastDto>,
    val crew: List<CrewDto>,
    val id: Int
)
