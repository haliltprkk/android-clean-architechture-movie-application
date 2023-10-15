package com.haliltprkk.movieapplication.common.extension

const val EMPTY_STRING = ""
const val ARG_ID = "ARG_ID"
fun String.toFullImageLink(): String {
    return "https://image.tmdb.org/t/p/w500/$this"
}
