package com.haliltprkk.movieapplication.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.haliltprkk.movieapplication.data.local.entities.GenreEntity
import com.haliltprkk.movieapplication.data.local.entities.ProductionCompanyEntity
import com.haliltprkk.movieapplication.data.local.entities.ProductionCountryEntity
import com.haliltprkk.movieapplication.data.local.entities.SpokenLanguageEntity
import com.haliltprkk.movieapplication.data.utils.JsonParser

@ProvidedTypeConverter
class DatabaseConverters(private val jsonParser: JsonParser) {

    @TypeConverter
    fun fromSpokenLanguagesJson(json: String): List<SpokenLanguageEntity> {
        return jsonParser.fromJson<ArrayList<SpokenLanguageEntity>>(
            json,
            object : TypeToken<ArrayList<SpokenLanguageEntity>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toSpokenLanguagesJson(movies: List<SpokenLanguageEntity>): String {
        return jsonParser.toJson(
            movies,
            object : TypeToken<ArrayList<SpokenLanguageEntity>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromGenresJson(json: String): List<GenreEntity> {
        return jsonParser.fromJson<ArrayList<GenreEntity>>(
            json,
            object : TypeToken<ArrayList<GenreEntity>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toGenresJson(movies: List<GenreEntity>): String {
        return jsonParser.toJson(
            movies,
            object : TypeToken<ArrayList<GenreEntity>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromProductionCompaniesJson(json: String): List<ProductionCompanyEntity> {
        return jsonParser.fromJson<ArrayList<ProductionCompanyEntity>>(
            json,
            object : TypeToken<ArrayList<ProductionCompanyEntity>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toProductionCompaniesJson(movies: List<ProductionCompanyEntity>): String {
        return jsonParser.toJson(
            movies,
            object : TypeToken<ArrayList<ProductionCompanyEntity>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromProductionCountriesJson(json: String): List<ProductionCountryEntity> {
        return jsonParser.fromJson<ArrayList<ProductionCountryEntity>>(
            json,
            object : TypeToken<ArrayList<ProductionCountryEntity>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toProductionCountriesJson(movies: List<ProductionCountryEntity>): String {
        return jsonParser.toJson(
            movies,
            object : TypeToken<ArrayList<ProductionCountryEntity>>() {}.type
        ) ?: "[]"
    }
}
