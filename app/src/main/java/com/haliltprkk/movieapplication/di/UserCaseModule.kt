package com.haliltprkk.movieapplication.di

import com.haliltprkk.movieapplication.domain.use_cases.home.GetPopularMoviesUseCase
import com.haliltprkk.movieapplication.domain.use_cases.home.GetPopularMoviesUseCaseImpl
import com.haliltprkk.movieapplication.domain.use_cases.movie_detail.GetMovieDetailUseCase
import com.haliltprkk.movieapplication.domain.use_cases.movie_detail.GetMovieDetailUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserCaseModule {
    @Binds
    abstract fun bindGetPopularMoviesUseCase(
        getPopularMoviesUseCaseImpl: GetPopularMoviesUseCaseImpl
    ): GetPopularMoviesUseCase

    @Binds
    abstract fun bindGetMovieDetailUseCase(
        getMovieDetailUseCaseImpl: GetMovieDetailUseCaseImpl
    ): GetMovieDetailUseCase
}
