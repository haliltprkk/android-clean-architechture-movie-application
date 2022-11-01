package com.haliltprkk.movieapplication.common.extension

import android.content.Context
import android.content.res.Resources.getSystem
import com.haliltprkk.movieapplication.R

val Int.toDp: Int get() = (this / getSystem().displayMetrics.density).toInt()
val Int.toPx: Int get() = (this * getSystem().displayMetrics.density).toInt()

fun Int.runTimeToReadableDuration(context: Context): String {
    val hours: Int = this / 60
    val minutes: Int = this % 60
    return context.getString(R.string.hourAndMinWithParam,hours,minutes)
}
