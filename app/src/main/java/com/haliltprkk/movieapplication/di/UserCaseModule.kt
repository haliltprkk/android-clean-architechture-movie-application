package com.haliltprkk.movieapplication.di

import com.haliltprkk.movieapplication.domain.use_cases.home.GetPopularMoviesUseCase
import com.haliltprkk.movieapplication.domain.use_cases.home.GetPopularMoviesUseCaseImpl
import com.haliltprkk.movieapplication.domain.use_cases.movie_detail.GetMovieDetailUseCase
import com.haliltprkk.movieapplication.domain.use_cases.movie_detail.GetMovieDetailUseCaseImpl
import com.haliltprkk.movieapplication.domain.use_cases.search.SearchMovieUseCase
import com.haliltprkk.movieapplication.domain.use_cases.search.SearchMovieUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserCaseModule {
    @Binds
    @Singleton
    abstract fun bindGetPopularMoviesUseCase(
        getPopularMoviesUseCaseImpl: GetPopularMoviesUseCaseImpl
    ): GetPopularMoviesUseCase

    @Binds
    @Singleton
    abstract fun bindGetMovieDetailUseCase(
        getMovieDetailUseCaseImpl: GetMovieDetailUseCaseImpl
    ): GetMovieDetailUseCase

    @Binds
    @Singleton
    abstract fun bindSearchMovieUseCase(
        searchMovieUseCaseImpl: SearchMovieUseCaseImpl
    ): SearchMovieUseCase
}
