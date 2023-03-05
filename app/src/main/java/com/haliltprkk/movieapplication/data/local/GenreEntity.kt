package com.haliltprkk.movieapplication.data.local

import androidx.room.Entity

@Entity
data class GenreEntity(
    val id: Int,
    val name: String
)
