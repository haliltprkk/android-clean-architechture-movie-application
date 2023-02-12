package com.haliltprkk.movieapplication.common.extension

import com.google.gson.Gson
import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.UiText
import com.haliltprkk.movieapplication.data.models.ErrorModelDto
import com.haliltprkk.movieapplication.data.models.toErrorModel
import retrofit2.HttpException

val gson = Gson()

fun HttpException.handleError(): UiText {
    val errorString = this.response()?.errorBody()?.string()
    errorString?.let {
        val errorModel = gson.fromJson(errorString, ErrorModelDto::class.java)?.toErrorModel()
        if (errorModel?.error != null)
            return UiText.DynamicString(errorModel.error)
        else
            return UiText.StringResource(R.string.unexpectedError)
    }
    return this.localizedMessage?.let { UiText.DynamicString(it) }
        ?: UiText.StringResource(R.string.unexpectedError)
}
