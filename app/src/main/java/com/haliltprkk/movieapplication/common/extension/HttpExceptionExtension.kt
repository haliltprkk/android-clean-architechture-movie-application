package com.haliltprkk.movieapplication.common.extension

import com.google.gson.Gson
import com.haliltprkk.movieapplication.R
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.data.remote.models.ErrorDto
import com.haliltprkk.movieapplication.data.remote.models.toErrorModel
import retrofit2.HttpException

val gson = Gson()

@Synchronized
fun HttpException.handleError(): UiText {
    val errorString = this.response()?.errorBody()?.string()
    errorString?.let {
        val errorModel = gson.fromJson(errorString, ErrorDto::class.java)?.toErrorModel()
        if (errorModel?.error != null)
            return UiText.DynamicString(errorModel.error)
        else
            return UiText.StringResource(R.string.unexpectedError)
    }
    return this.localizedMessage?.let { UiText.DynamicString(it) }
        ?: UiText.StringResource(R.string.unexpectedError)
}
