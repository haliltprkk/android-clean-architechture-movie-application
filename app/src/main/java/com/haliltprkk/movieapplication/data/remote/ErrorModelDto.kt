package com.haliltprkk.movieapplication.data.remote

import com.haliltprkk.movieapplication.domain.models.ErrorModel

data class ErrorModelDto(val error: String?)

fun ErrorModelDto.toErrorModel(): ErrorModel = ErrorModel(error = error)
