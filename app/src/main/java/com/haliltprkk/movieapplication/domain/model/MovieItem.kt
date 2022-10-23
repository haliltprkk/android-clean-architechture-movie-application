package com.haliltprkk.movieapplication.domain.model

data class MovieItem(val id: String) {
    companion object {
        fun getDummyList() = arrayListOf(
            MovieItem(""),
            MovieItem(""),
            MovieItem(""),
            MovieItem(""),
            MovieItem("")
        )
    }
}
