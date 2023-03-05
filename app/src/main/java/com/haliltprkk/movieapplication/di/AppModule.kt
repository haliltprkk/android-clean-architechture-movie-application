package com.haliltprkk.movieapplication.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.haliltprkk.movieapplication.BuildConfig
import com.haliltprkk.movieapplication.data.local.DatabaseConverters
import com.haliltprkk.movieapplication.data.local.MovieDatabase
import com.haliltprkk.movieapplication.data.services.localStorage.KeyValueStore
import com.haliltprkk.movieapplication.data.services.localStorage.SharedPreferencesKeyValueStore
import com.haliltprkk.movieapplication.data.utils.GsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.*

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideSharedPreferencesKeyValueStore(preferences: SharedPreferences): KeyValueStore =
        SharedPreferencesKeyValueStore(preferences)

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences(
            BuildConfig.APPLICATION_ID,
            Activity.MODE_PRIVATE
        )

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application, gson: Gson): MovieDatabase {
        return Room.databaseBuilder(app, MovieDatabase::class.java, "movie_db")
            .addTypeConverter(DatabaseConverters(GsonParser(gson)))
            .build()
    }
}
