package com.haliltprkk.movieapplication.common.extension

import android.content.res.Resources.getSystem

val Float.toDp: Float get() = (this / getSystem().displayMetrics.density)
val Float.toPx: Float get() = (this * getSystem().displayMetrics.density)
