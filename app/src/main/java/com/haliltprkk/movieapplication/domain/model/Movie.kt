package com.haliltprkk.movieapplication.domain.model

import com.google.gson.Gson
import com.haliltprkk.movieapplication.data.remote.MovieDto
import com.haliltprkk.movieapplication.data.remote.toMovie

data class Movie(
    val id: Long,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val title: String,
    val overview: String,
    val genre: String,
    val runtime: Int
) {
    companion object {
        fun getMockMovie(): Movie {
            //TODO write helper for this and use injection
            val gson = Gson()
            val movieDto = gson.fromJson(
                "{ \"adult\": false, \"backdrop_path\": \"/8sMmAmN2x7mBiNKEX2o0aOTozEB.jpg\", \"genre_ids\": [ 28, 12, 878 ], \"id\": 505642, \"original_language\": \"en\", \"original_title\": \"Black Panther: Wakanda Forever\", \"overview\": \"Queen Ramonda, Shuri, M’Baku, Okoye and the Dora Milaje fight to protect their nation fr\\n om intervening world powers in the wake of King T’Challa’s death. As the Wakandans strive to embrace their nex\\n t chapter, the heroes must band together with the help of War Dog Nakia and Everett Ross and forge a new path \\n for the kingdom of Wakanda.\", \"popularity\": 3281.982, \"poster_path\": \"/sv1xJUazXeYqALzczSZ3O6nkH75.jpg\", \"release_date\": \"2022-11-09\", \"title\": \"Black Panther: Wakanda Forever\", \"video\": false, \"vote_average\": 7.6, \"vote_count\": 347 }",
                MovieDto::class.java
            )
            return movieDto.toMovie()
        }
    }
}