package com.haliltprkk.movieapplication.data.remote.models

import com.haliltprkk.movieapplication.domain.models.ErrorModel

data class ErrorDto(val error: String?)

fun ErrorDto.toErrorModel(): ErrorModel = ErrorModel(error = error)
