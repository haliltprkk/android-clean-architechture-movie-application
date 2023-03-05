package com.haliltprkk.movieapplication.data.local
import androidx.room.Entity

@Entity
data class ProductionCompanyEntity(
    val id: Int,
    val logoPath: String,
    val name: String,
    val originCountry: String
)
