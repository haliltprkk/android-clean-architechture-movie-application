package com.haliltprkk.movieapplication.data.remote

import com.haliltprkk.movieapplication.domain.model.ErrorModel

data class ErrorModelDto(val error: String?)

fun ErrorModelDto.toErrorModel(): ErrorModel {
    return ErrorModel(error = error)
}
