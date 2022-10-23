package com.haliltprkk.movieapplication.common.extension

import android.content.res.Resources.getSystem

val Int.toDp: Int get() = (this / getSystem().displayMetrics.density).toInt()
val Int.toPx: Int get() = (this * getSystem().displayMetrics.density).toInt()
