package com.haliltprkk.movieapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.haliltprkk.movieapplication.data.local.entities.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract val dao: MovieDao
}
