package com.haliltprkk.movieapplication.di

import com.haliltprkk.movieapplication.domain.usecases.home.GetPopularMoviesUseCase
import com.haliltprkk.movieapplication.domain.usecases.home.GetPopularMoviesUseCaseImpl
import com.haliltprkk.movieapplication.domain.usecases.moviedetail.GetMovieCastsUseCase
import com.haliltprkk.movieapplication.domain.usecases.moviedetail.GetMovieCastsUseCaseImpl
import com.haliltprkk.movieapplication.domain.usecases.moviedetail.GetMovieDetailUseCase
import com.haliltprkk.movieapplication.domain.usecases.moviedetail.GetMovieDetailUseCaseImpl
import com.haliltprkk.movieapplication.domain.usecases.search.SearchMovieUseCase
import com.haliltprkk.movieapplication.domain.usecases.search.SearchMovieUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
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

    @Binds
    @Singleton
    abstract fun bindCastUseCase(
        getMovieCastsUseCase: GetMovieCastsUseCaseImpl
    ): GetMovieCastsUseCase
}
