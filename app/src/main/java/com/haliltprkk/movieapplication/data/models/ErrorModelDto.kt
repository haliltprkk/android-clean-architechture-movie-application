package com.haliltprkk.movieapplication.data.models

import com.haliltprkk.movieapplication.domain.models.ErrorModel

data class ErrorModelDto(val error: String?)

fun ErrorModelDto.toErrorModel(): ErrorModel {
    return ErrorModel(error = error)
}
