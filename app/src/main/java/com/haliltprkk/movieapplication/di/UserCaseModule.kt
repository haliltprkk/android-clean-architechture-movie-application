package com.haliltprkk.movieapplication.di

import com.haliltprkk.movieapplication.domain.use_cases.home.GetPopularMoviesUseCase
import com.haliltprkk.movieapplication.domain.use_cases.home.GetPopularMoviesUseCaseImpl
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
}
