package com.haliltprkk.movieapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haliltprkk.movieapplication.data.local.entities.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<MovieEntity>
}
