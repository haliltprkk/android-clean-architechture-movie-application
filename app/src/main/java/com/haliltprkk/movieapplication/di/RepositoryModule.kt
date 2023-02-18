package com.haliltprkk.movieapplication.di

import com.haliltprkk.movieapplication.data.api_services.MovieApiService
import com.haliltprkk.movieapplication.data.repositories.MovieRepositoryImpl
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieApiService): MovieRepository =
        MovieRepositoryImpl(api)
}
