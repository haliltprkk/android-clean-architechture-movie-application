package com.haliltprkk.movieapplication.domain.use_cases.home

import com.google.common.truth.Truth
import com.haliltprkk.movieapplication.common.Resource
import com.haliltprkk.movieapplication.common.UiText
import com.haliltprkk.movieapplication.domain.repositories.MovieRepository
import com.haliltprkk.movieapplication.fake_db.MockHelper
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
    private val page: Int = 0

    @Before
    fun setUp() {
        getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository)
    }

    @Test
    fun `check getPopularMovies() success case`() = runBlocking {
        // when
        whenever(movieRepository.getPopularMovies(page)).thenAnswer { MockHelper.popularMovieListDto }
        val result = getPopularMoviesUseCase(page)
        val flowList = result.toList()
        // then
        Truth.assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(flowList[1]).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `check getPopularMovies() http exception error case`() = runBlocking {
        // when
        whenever(movieRepository.getPopularMovies(page)).thenAnswer { throw MockHelper.httpException }
        val result = getPopularMoviesUseCase(page)
        val flowList = result.toList()
        // then
        Truth.assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat(flowList[1].message).isInstanceOf(UiText.DynamicString::class.java)
    }

    @Test
    fun `check getPopularMovies() io exception error case`() = runBlocking {
        // when
        whenever(movieRepository.getPopularMovies(page)).thenAnswer { throw MockHelper.ioException }
        val result = getPopularMoviesUseCase(page)
        val flowList = result.toList()
        // then

        Truth.assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        Truth.assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat(flowList[1].message).isInstanceOf(UiText.StringResource::class.java)
    }
}
