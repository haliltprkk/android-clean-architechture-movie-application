package com.haliltprkk.movieapplication.domain.models

data class MovieItem(val id: String) {
    companion object {
        fun getMockList() = arrayListOf(
            MovieItem(""),
            MovieItem(""),
            MovieItem(""),
            MovieItem(""),
            MovieItem("")
        )
    }
}
