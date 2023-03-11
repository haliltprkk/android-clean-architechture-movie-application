package com.haliltprkk.movieapplication.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.haliltprkk.movieapplication.data.local.entities.GenreEntity
import com.haliltprkk.movieapplication.data.utils.JsonParser

@ProvidedTypeConverter
class DatabaseConverters(private val jsonParser: JsonParser) {

    @TypeConverter
    fun fromGenresJson(json: String): List<GenreEntity> {
        return jsonParser.fromJson<ArrayList<GenreEntity>>(
            json,
            object : TypeToken<ArrayList<GenreEntity>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toGenresJson(movies: List<GenreEntity>?): String {
        return jsonParser.toJson(
            movies,
            object : TypeToken<ArrayList<GenreEntity>>() {}.type
        ) ?: "[]"
    }
}
