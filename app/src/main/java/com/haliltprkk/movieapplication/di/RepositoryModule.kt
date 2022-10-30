package com.haliltprkk.movieapplication.di

import com.haliltprkk.movieapplication.data.remote.MovieService
import com.haliltprkk.movieapplication.data.repository.MovieRepositoryImpl
import com.haliltprkk.movieapplication.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieService): MovieRepository =
        MovieRepositoryImpl(api)
}