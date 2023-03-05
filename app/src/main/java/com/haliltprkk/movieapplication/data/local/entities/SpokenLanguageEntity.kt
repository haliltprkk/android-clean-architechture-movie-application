package com.haliltprkk.movieapplication.data.local.entities

import androidx.room.Entity

@Entity
data class SpokenLanguageEntity(
    val englishName: String,
    val iso6391: String,
    val name: String
)
