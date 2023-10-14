package com.haliltprkk.movieapplication.domain.usecases.home

import com.google.common.truth.Truth.assertThat
import com.haliltprkk.movieapplication.common.utils.Resource
import com.haliltprkk.movieapplication.common.utils.UiText
import com.haliltprkk.movieapplication.data.services.localStorage.LocalStorageService
import com.haliltprkk.movieapplication.domain.mappers.MovieMapper
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import com.haliltprkk.movieapplication.utils.MockHelper
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesUseCaseTest {
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var localeStorageService: LocalStorageService

    @Mock
    private lateinit var mapper: MovieMapper
    private val page: Int = 0

    @Before
    fun setUp() {
        getPopularMoviesUseCase = GetPopularMoviesUseCaseImpl(
            movieRepository,
            localeStorageService,
            mapper
        )
    }

    @Test
    fun `check getPopularMovies() success case`() = runBlocking {
        // when
        whenever(movieRepository.getPopularMovies(page)).thenAnswer { MockHelper.popularMovieListDto }
        val result = getPopularMoviesUseCase.getPopularMovies(page)
        val flowList = result.toList()
        // then
        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `check getPopularMovies() http exception error case`() = runBlocking {
        // when
        whenever(movieRepository.getPopularMovies(page)).thenAnswer { throw MockHelper.getHttpException() }
        val result = getPopularMoviesUseCase.getPopularMovies(page)
        val flowList = result.toList()
        // then
        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        assertThat(flowList[1].message).isInstanceOf(UiText.DynamicString::class.java)
    }

    @Test
    fun `check getPopularMovies() io exception error case`() = runBlocking {
        // when
        whenever(movieRepository.getPopularMovies(page)).thenAnswer { throw MockHelper.ioException }
        val result = getPopularMoviesUseCase.getPopularMovies(page)
        val flowList = result.toList()
        // then

        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        assertThat(flowList[1].message).isInstanceOf(UiText.StringResource::class.java)
    }
}
