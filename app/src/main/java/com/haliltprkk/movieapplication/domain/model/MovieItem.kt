package com.haliltprkk.movieapplication.domain.model

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
