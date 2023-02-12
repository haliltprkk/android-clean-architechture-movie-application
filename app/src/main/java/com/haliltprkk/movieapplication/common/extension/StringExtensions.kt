package com.haliltprkk.movieapplication.common.extension

fun String.toFullImageLink(): String {
    return "https://image.tmdb.org/t/p/w500/$this"
}
